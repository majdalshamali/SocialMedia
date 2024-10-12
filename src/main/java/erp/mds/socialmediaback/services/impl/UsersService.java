package erp.mds.socialmediaback.services.impl;

import erp.mds.socialmediaback.dto.response.UserDto;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.exception.ResourceAlreadyExistException;
import erp.mds.socialmediaback.mapper.Mapper;
import erp.mds.socialmediaback.repository.UsersRepository;
import erp.mds.socialmediaback.security.UsersDetails;
import erp.mds.socialmediaback.services.IUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersService implements IUsersService {

    private  final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Users save(Users user) {

        List<Users> existedUsers = usersRepository.findByUserNameOrEmail(user.getEmail(),user.getUserName());
        if(!existedUsers.isEmpty() && user.getId() == null){
            throw new ResourceAlreadyExistException("User already exist with the given UserName or Email");
        }
        return usersRepository.save(user);
    }

    @Override
    public Optional<UsersDetails> findByUserName(String userName) {
        return usersRepository.findByUserName(userName)
                .map(UsersDetails::new) // Convert Users to UsersDetails if present
                .or(Optional::empty); // Return Optional.empty if not present
    }

    @Override
    public Optional<Users> getUser() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;

        // Check if the authentication is of type UsernamePasswordAuthenticationToken
        if(authentication != null) {
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                Object principal = authentication.getPrincipal();

                // If the principal is an instance of UserDetails, retrieve the username
                if (principal instanceof UsersDetails) {
                    return Optional.ofNullable(((UsersDetails) principal).getUser());
                }
            }
        }
        return Optional.empty(); // or handle it appropriately if authentication is null or not an expected type
    }
}
