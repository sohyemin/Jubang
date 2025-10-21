package HelloWorld.Jubang.config;

import HelloWorld.Jubang.config.filter.XssFilter;
import HelloWorld.Jubang.domain.user.entity.User;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // Spring Security 설정을 활성화
@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity // @PreAuthorize, @Secured, @RolesAllowed 어노테이션 사용을 위해 필요
public class SecurityConfig {

    private final XssFilter xssFilter;
    private final JWTCheckFilter jwtCheckFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                        .requestMatchers("/api/v1/user/**", "/api/v1/room/**").permitAll()
                        .anyRequest().authenticated()
        );

        // 필터 순서: XSS Filter → JWT Filter → UsernamePasswordAuthenticationFilter
        // 1. XSS 필터를 가장 먼저 추가 (사용자 입력 검증)
        http.addFilterBefore(xssFilter, UsernamePasswordAuthenticationFilter.class);

        // 2. JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 추가 (인증 처리)
        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

        // exception authenticationEntryPoint 추가 401 에러 처리
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(customAuthenticationEntryPoint);
        });
        // exceptionHandler, 접근 거부 핸들러 추가
        http.exceptionHandling(config -> {
            config.accessDeniedHandler(customAccessDeniedHandler);
        });
        return http.build();
    }
}

