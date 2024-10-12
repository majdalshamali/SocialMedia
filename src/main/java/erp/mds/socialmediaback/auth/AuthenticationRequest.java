package erp.mds.socialmediaback.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Authentication Request",
        description = "Schema to hold User Credentials"
)
public class AuthenticationRequest {

    @NotEmpty(message = "User Name can not be a null or empty")
    @Schema(
            description = "User Name"
    )
    private String userName;

    @NotEmpty(message = "Password can not be a null or empty")
    @Schema(
            description = "Password"
    )
    String password;
}