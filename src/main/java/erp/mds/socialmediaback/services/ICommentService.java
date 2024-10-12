package erp.mds.socialmediaback.services;

import erp.mds.socialmediaback.dto.request.CommentDtoRequest;
import erp.mds.socialmediaback.dto.response.CommentDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Comment;

import java.util.List;

public interface ICommentService {

    Comment  save(CommentDtoRequest commentDto, Users user);

    List<CommentDtoResponse> listOfCommentsOfPost(Integer postId);
}
