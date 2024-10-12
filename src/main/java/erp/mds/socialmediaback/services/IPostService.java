package erp.mds.socialmediaback.services;

import erp.mds.socialmediaback.dto.request.PostDtoRequest;
import erp.mds.socialmediaback.dto.response.PostDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    Post save(PostDtoRequest postDto, Users user);
    Optional<Post> get(Integer id);
    Optional<PostDtoResponse> getFullPost(Integer id);
    Optional<PostDtoResponse> getSmallPost(Integer id);
    List<PostDtoResponse> listOfAllPost(String keyword);
    List<PostDtoResponse> search(String keyword,Integer pageNum);
}
