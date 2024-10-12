package erp.mds.socialmediaback.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Authentication Response",
        description = "Schema to hold Access Token (JWT), Refresh Token, API-Key"
)
public class AuthenticationResponse {

    @Schema(
            description = "Access Token JWT Bearer"
    )
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(
            description = "Refresh Token JWT Bearer"
    )
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(
            description = "API key"
    )
    @JsonProperty("x-api-key")
    private String apiKey;
}