package erp.mds.socialmediaback.repository;

import erp.mds.socialmediaback.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
            SELECT t FROM Token t\s
            WHERE t.user.id = :id AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(@Param("id") Long id);

    Optional<Token> findByToken(String token);
}
