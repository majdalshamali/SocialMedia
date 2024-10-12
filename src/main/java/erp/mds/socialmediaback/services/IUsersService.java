package erp.mds.socialmediaback.services;

import erp.mds.socialmediaback.dto.response.UserDto;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.security.UsersDetails;

import java.util.Optional;

public interface IUsersService {

    Users save(Users user);
    Optional<UsersDetails> findByUserName(String userName);

    public Optional<Users> getUser();




}
