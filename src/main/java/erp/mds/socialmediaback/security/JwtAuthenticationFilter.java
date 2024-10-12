package erp.mds.socialmediaback.security;

import erp.mds.socialmediaback.constant.SocialConstant;
import erp.mds.socialmediaback.repository.TokenRepository;
import erp.mds.socialmediaback.exception.ResourceNotFoundException;
import erp.mds.socialmediaback.services.impl.UsersService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsersService usersService;
    private final TokenRepository tokenRepository;
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        for (String url : SocialConstant.WHITE_LIST_URL) {
            if (servletPath.startsWith(url)) {
                filterChain.doFilter(request, response);
                return; // Bypass JWT check for whitelisted URLs
            }
        }
        final String authHeader = request.getHeader("Authorization");
        final String apiKey = request.getHeader("x-api-key");
        final String jwtToken;
        String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(apiKey == null){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userName = jwtService.extractUserName(jwtToken);
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.usersService.findByUserName(userName)
                    .orElseThrow(() -> new ResourceNotFoundException("Users", "User Name", userName));

            var isTokenValid = tokenRepository.findByToken(jwtToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwtToken, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }
        filterChain.doFilter(request, response);

    }
}
