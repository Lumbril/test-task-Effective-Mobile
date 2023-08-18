package effective.mobile.code.controllers.rest;

import effective.mobile.code.utils.jwt.dto.request.UserLoginRequest;
import effective.mobile.code.utils.jwt.dto.request.UserRegisterRequest;
import effective.mobile.code.utils.jwt.dto.response.JwtTokenPairResponse;
import effective.mobile.code.entities.User;
import effective.mobile.code.services.UserServices;
import effective.mobile.code.utils.jwt.services.JwtAuthenticationService;
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
    private final JwtAuthenticationService jwtAuthenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userServices.getAll());
    }

    @PostMapping("/registration")
    public ResponseEntity<JwtTokenPairResponse> registration(
            @RequestBody UserRegisterRequest userRegisterRequest
            ) {
        return ResponseEntity.ok().body(
                jwtAuthenticationService.register(userRegisterRequest)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenPairResponse> login(
            @RequestBody UserLoginRequest userLoginRequest
            ) {
        return ResponseEntity.ok().body(
                jwtAuthenticationService.authenticate(userLoginRequest)
        );
    }
}
