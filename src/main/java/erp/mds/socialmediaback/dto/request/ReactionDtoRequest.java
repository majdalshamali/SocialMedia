package erp.mds.socialmediaback.dto.request;

import erp.mds.socialmediaback.entity.business.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(
        name = "Add Reaction Request",
        description = "Schema to hold Reaction information"
)
public class ReactionDtoRequest {

    @NotEmpty(message = "Post ID can not be a null or empty")
    @Min(value = 0, message = "postId should be greater or equal  0")
    @Schema(
            description = "Post ID"
    )
    Integer postId;

    @NotEmpty(message = "Reaction Type can not be a null or empty")
    @Schema(
            description = "Reaction Type Should be on of the values (LIKE,LOVE,DISLIKE)"
    )
    ReactionType reactionType;
}
