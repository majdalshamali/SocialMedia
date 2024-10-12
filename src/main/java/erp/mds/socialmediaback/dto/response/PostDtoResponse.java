package erp.mds.socialmediaback.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Schema(
        name = "Post Response",
        description = "Schema to hold Added Post information"
)
public class PostDtoResponse {

    @Schema(
            description = "Post ID of the Saved Post"
    )
    private Integer id;

    @Schema(
            description = "text of the Saved Post"
    )
    private String text;

    @Schema(
            description = "User information of Saved Post"
    )
    private UserDto user;

    @Schema(
            description = "Count of likes of the Saved Post"
    )
    private Integer countOfLikes;

    @Schema(
            description = "Count of comments of the Saved Post"
    )
    private Integer countOfComments;

    @Schema(
            description = "created at of the Saved Post"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "created by of the Saved Post"
    )
    private String createdBy;

    @Schema(
            description = "comments of the Saved Post. it is null if the request of smallPost"
    )
    private List<CommentDtoResponse> comments;

    @Schema(
            description = "reactions of the Saved Post. it is null if the request of smallPost"
    )
    private List<ReactionDtoResponse> reactions;

}
