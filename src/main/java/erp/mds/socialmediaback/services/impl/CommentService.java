package erp.mds.socialmediaback.services.impl;

import erp.mds.socialmediaback.dto.request.CommentDtoRequest;
import erp.mds.socialmediaback.dto.response.CommentDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Comment;
import erp.mds.socialmediaback.entity.business.Post;
import erp.mds.socialmediaback.exception.ResourceNotFoundException;
import erp.mds.socialmediaback.mapper.Mapper;
import erp.mds.socialmediaback.repository.CommentRepository;
import erp.mds.socialmediaback.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    @Override
    public Comment save(CommentDtoRequest commentDto, Users user) {
        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .user(user)
                .post(new Post(commentDto.getPostId()))
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDtoResponse> listOfCommentsOfPost(Integer postId) {
        List<Comment> comments = commentRepository.listOfPostComment(postId);
        if(!comments.isEmpty()) {
            List<CommentDtoResponse> commentDtoResponsesList = new ArrayList<>();
            comments.forEach(comment -> {
                commentDtoResponsesList.add(Mapper.toCommentDtoResponse(comment));
            });
            return commentDtoResponsesList;
        }
        else {
            throw new ResourceNotFoundException("Comments","POST ID",""+postId);
        }
    }

}
