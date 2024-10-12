package erp.mds.socialmediaback.dto.request;


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
        name = "Add Comment Request",
        description = "Schema to hold Comment information"
)
public class CommentDtoRequest {

    @Min(value = 0, message = "postId should be greater or equal  0")
    @Schema(
            description = "Post ID"
    )
    Integer postId;

    @NotEmpty(message = "Text can not be a null or empty")
    @Schema(
            description = "Comment text"
    )
    String text;
}
