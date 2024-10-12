package erp.mds.socialmediaback.repository;

import erp.mds.socialmediaback.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    @Query("SELECT u FROM Users u WHERE u.email = ?1 Or u.userName = ?2")
    public List<Users> findByUserNameOrEmail(String email, String userName);

    Optional<Users> findByUserName(String userName);

}
