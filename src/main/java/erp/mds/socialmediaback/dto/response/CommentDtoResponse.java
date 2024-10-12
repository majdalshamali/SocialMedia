package erp.mds.socialmediaback.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Comment Response",
        description = "Schema to hold Comment information"
)
public class CommentDtoResponse {

    @Schema(
            description = "ID  in the Saved Comment"
    )
    private Integer id;

    @Schema(
            description = "text  in the Saved Comment"
    )
    private String text;

    @Schema(
            description = "Created At value  in the Saved Comment"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Created By  in the Saved Comment"
    )
    private String createdBy;


    @Schema(
            description = "User Info in the Saved Comment"
    )
    private UserDto user;

}
