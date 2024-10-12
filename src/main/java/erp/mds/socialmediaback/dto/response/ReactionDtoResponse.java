package erp.mds.socialmediaback.dto.response;

import erp.mds.socialmediaback.entity.business.ReactionType;
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
        name = "Reaction Response",
        description = "Schema to hold saved Reaction information"
)
public class ReactionDtoResponse {

    @Schema(
            description = "Reaction ID of the Saved Post"
    )
    private Integer id;

    @Schema(
            description = "Reaction Type of the Saved Post one of the values (LIKE,LOVE,DISLIKE)"
    )
    private ReactionType reactionType;

    @Schema(
            description = "Created at  of the Saved Post"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Created By  of the Saved Post"
    )
    private String createdBy;

    @Schema(
            description = "User information  of the Saved Post"
    )
    private UserDto user;

}
