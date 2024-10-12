package erp.mds.socialmediaback.repository;


import erp.mds.socialmediaback.entity.business.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>, PagingAndSortingRepository<Post,Integer> {

    @Query("select p from Post p where p.text LIKE %?1%")
    List<Post> listOfPost(String keyword);
    @Query("select p from Post p where p.text LIKE %?1%")
    Page<Post> search(String keyword, Pageable page);
}
