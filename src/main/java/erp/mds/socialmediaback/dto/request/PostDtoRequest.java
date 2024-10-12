package erp.mds.socialmediaback.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(
        name = "Add Post Request",
        description = "Schema to hold Post information"
)
public class PostDtoRequest {

    @NotEmpty(message = "Text can not be a null or empty")
    @Schema(
            description = "Post text"
    )
    private String text;
}
