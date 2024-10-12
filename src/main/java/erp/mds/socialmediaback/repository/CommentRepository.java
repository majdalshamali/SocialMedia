package erp.mds.socialmediaback.repository;


import erp.mds.socialmediaback.entity.business.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {


    @Query("select c from Comment c where c.post.id = ?1")
    List<Comment> listOfPostComment(Integer postId);

}
