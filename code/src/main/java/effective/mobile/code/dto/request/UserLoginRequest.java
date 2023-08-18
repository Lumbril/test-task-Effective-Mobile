package effective.mobile.code.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginRequest {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
}
