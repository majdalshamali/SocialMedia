package erp.mds.socialmediaback.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
        name = "User",
        description = "Schema to hold User information"
)
public class RegisterRequest {

    @NotEmpty(message = "First Name can not be a null or empty")
    @Schema(
            description = "First Name"
    )
    private String firstName;


    @NotEmpty(message = "Last Name can not be a null or empty")
    @Schema(
            description = "Last Name"
    )
    private String lastName;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    @Schema(
            description = "Email"
    )
    private String email;

    @NotEmpty(message = "User Name can not be a null or empty")
    @Schema(
            description = "User Name"
    )
    private String userName;

    @NotEmpty(message = "Password can not be a null or empty")
    @Schema(
            description = "Password"
    )
    private String password;
}
