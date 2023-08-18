package effective.mobile.code.utils.jwt.services;

import effective.mobile.code.entities.User;
import effective.mobile.code.entities.enums.Role;
import effective.mobile.code.repositories.UserRepository;
import effective.mobile.code.utils.jwt.dto.request.UserLoginRequest;
import effective.mobile.code.utils.jwt.dto.request.UserRegisterRequest;
import effective.mobile.code.utils.jwt.dto.response.JwtTokenPairResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtTokenPairResponse register(UserRegisterRequest request) {
        User user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        //saveUserToken(savedUser, jwtToken);

        return JwtTokenPairResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtTokenPairResponse authenticate(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getLogin(),
                        userLoginRequest.getPassword()
                )
        );

        User user = userRepository.findByLogin(userLoginRequest.getLogin())
                .orElseThrow();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        System.out.println(jwtService.isTokenValid(accessToken));

        return JwtTokenPairResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
