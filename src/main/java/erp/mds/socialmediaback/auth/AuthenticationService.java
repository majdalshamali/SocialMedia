package erp.mds.socialmediaback.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import erp.mds.socialmediaback.entity.Role;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.token.Token;
import erp.mds.socialmediaback.repository.TokenRepository;
import erp.mds.socialmediaback.entity.token.TokenType;
import erp.mds.socialmediaback.exception.ResourceNotFoundException;
import erp.mds.socialmediaback.security.JwtService;
import erp.mds.socialmediaback.security.UsersDetails;
import erp.mds.socialmediaback.services.impl.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersService usersService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    //This function to create New User
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .userName(request.getUserName())
                .enabled(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MOBILE)
                .build();

        var savedUser = usersService.save(user);//save the user
        var jwtToken = jwtService.generateToken(new UsersDetails(savedUser));//create New Token
        var refreshToken = jwtService.generateRefreshToken(new UsersDetails(savedUser));//Create Refresh Token
        String apiKey = UUID.randomUUID().toString();//Create Api Key
        saveUserToken(savedUser, jwtToken,apiKey);//save all type of token in the repository
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .apiKey(apiKey)
                .build();//return the auth information
    }

    //function to login
    //user should enter userName and Password
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        //check of the user exists or throw exception
        var user = usersService.findByUserName(request.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Name", request.getUserName()));
        //create new JWT token
        var jwtToken = jwtService.generateToken(user);

        //Create new Refresh token
        var refreshToken = jwtService.generateRefreshToken(user);

        //Create new Api Key
        String apiKey = UUID.randomUUID().toString();

        //Revoke all the existed token
        revokeAllUserTokens(user.getUser());

        //Save the new tokens
        saveUserToken(user.getUser(), jwtToken,apiKey);

        //return the information
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .apiKey(apiKey)
                .build();
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(Users user, String jwtToken,String apiToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        token = Token.builder()
                .user(user)
                .token(apiToken)
                .tokenType(TokenType.APIKEY)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null) {
            var user = this.usersService.findByUserName(userName)
                    .orElseThrow(() -> new ResourceNotFoundException("Users", "User Name", userName));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user.getUser());
                String apiKey = UUID.randomUUID().toString();
                saveUserToken(user.getUser(), accessToken,apiKey);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .apiKey(apiKey)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }



}
