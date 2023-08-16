package effective.mobile.code.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*httpSecurity
                .authorizeRequests()
                .antMatchers(
                        "/", "/login", "/resources/**", "/static/**", "/test/**", "/question/**",
                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger/**"
                ).permitAll();*/
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(
                                "/**", "/resources/**", "/static/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger/**")
                        .permitAll()
                );

        return http.build();
    }
}
