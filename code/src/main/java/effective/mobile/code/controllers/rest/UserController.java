package effective.mobile.code.controllers.rest;

import effective.mobile.code.dto.request.UserLoginRequest;
import effective.mobile.code.dto.response.JwtTokenPairResponse;
import effective.mobile.code.entities.User;
import effective.mobile.code.services.UserServices;
import effective.mobile.code.utils.jwt.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "user API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;
    private final AuthenticationService authenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userServices.getAll());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenPairResponse> login(
            @RequestBody UserLoginRequest userLoginRequest
            ) {
        return ResponseEntity.ok().body(
                new JwtTokenPairResponse("sdfdsf", "dsdfsdf")
        );
    }
}
