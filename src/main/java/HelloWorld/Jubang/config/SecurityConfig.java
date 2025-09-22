package HelloWorld.Jubang.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // Spring Security 설정을 활성화
@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity // @PreAuthorize, @Secured, @RolesAllowed 어노테이션 사용을 위해 필요
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("security config");

        http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/api/v1/user/**").permitAll()
                        .anyRequest().authenticated()
        );

        return http.build();
    }
}
