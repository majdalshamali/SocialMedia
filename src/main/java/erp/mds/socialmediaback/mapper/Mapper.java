package erp.mds.socialmediaback.mapper;

import erp.mds.socialmediaback.dto.response.CommentDtoResponse;
import erp.mds.socialmediaback.dto.response.PostDtoResponse;
import erp.mds.socialmediaback.dto.response.ReactionDtoResponse;
import erp.mds.socialmediaback.dto.response.UserDto;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Comment;
import erp.mds.socialmediaback.entity.business.Post;
import erp.mds.socialmediaback.entity.business.Reaction;

public class Mapper {

    public static Users mapDtoToUser(UserDto userDto)
    {
        return Users.builder()
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserDto mapUserToDto(Users user)
    {
        return UserDto.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    public static PostDtoResponse toPostDtoResponse(Post post)
    {
        return PostDtoResponse.builder()
                .id(post.getId())
                .user(mapUserToDto(post.getUser()))
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
                .text(post.getText())
                .countOfComments(post.getComments().size())
                .countOfLikes(post.getReactions().size())
                .build();
    }

    public static CommentDtoResponse toCommentDtoResponse(Comment comment)
    {
        return CommentDtoResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .createdBy(comment.getCreatedBy())
                .user(mapUserToDto(comment.getUser()))
                .build();
    }

    public static ReactionDtoResponse toReactionDtoResponse(Reaction reaction)
    {
        return ReactionDtoResponse.builder()
                .id(reaction.getId())
                .reactionType(reaction.getReactionType())
                .createdAt(reaction.getCreatedAt())
                .createdBy(reaction.getCreatedBy())
                .user(mapUserToDto(reaction.getUser()))
                .build();
    }

}
