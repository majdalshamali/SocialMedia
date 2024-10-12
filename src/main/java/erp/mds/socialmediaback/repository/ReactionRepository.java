package erp.mds.socialmediaback.repository;


import erp.mds.socialmediaback.entity.business.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {


    @Query("select r from Reaction r where r.post.id = ?1")
    List<Reaction> listOfReactionOfPost(Integer postId);
}
