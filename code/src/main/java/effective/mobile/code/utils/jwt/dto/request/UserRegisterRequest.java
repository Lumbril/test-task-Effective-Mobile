package effective.mobile.code.utils.jwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
    @JsonProperty("login")
    private String login;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
