package effective.mobile.code.controllers.rest;

import effective.mobile.code.dto.response.PostPageResponse;
import effective.mobile.code.services.PostService;
import effective.mobile.code.utils.jwt.dto.request.RefreshTokenRequest;
import effective.mobile.code.utils.jwt.dto.request.UserLoginRequest;
import effective.mobile.code.utils.jwt.dto.request.UserRegisterRequest;
import effective.mobile.code.utils.jwt.dto.response.JwtErrorResponse;
import effective.mobile.code.utils.jwt.dto.response.JwtTokenPairResponse;
import effective.mobile.code.entities.User;
import effective.mobile.code.services.UserService;
import effective.mobile.code.utils.jwt.exceptions.InvalidTokenException;
import effective.mobile.code.utils.jwt.services.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "User", description = "user API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final JwtAuthenticationService jwtAuthenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAll());
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        try {
            return ResponseEntity.ok().body(
                    jwtAuthenticationService.refreshTokens(refreshTokenRequest)
            );
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(
                    JwtErrorResponse.builder()
                            .error(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<PostPageResponse> posts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            Principal principal
    ) {
        return ResponseEntity.ok().body(
                postService.getPostsForUser(principal, page, size)
        );
    }

/*    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidToken(InvalidTokenException exc) {
        return ResponseEntity.notFound().build();
    }*/
}
