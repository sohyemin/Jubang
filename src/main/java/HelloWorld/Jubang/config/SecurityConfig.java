package HelloWorld.Jubang.config;

import HelloWorld.Jubang.config.filter.JWTCheckFilter;
import HelloWorld.Jubang.config.filter.XssFilter;
import HelloWorld.Jubang.security.handler.CustomAccessDeniedHandler;
import HelloWorld.Jubang.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

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
        .cors(withDefaults())
        .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/api/v1/user/join", "/api/v1/user/login", "/api/v1/user/check-email", "/api/v1/user/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/api/v1/room/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", "/health", "/favicon.ico", "/static/**").permitAll()
                        .requestMatchers("/api/v1/user/logout").authenticated()
                        .requestMatchers("/api/v1/user/reservation").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user").authenticated()
                        .requestMatchers(HttpMethod.POST,   "/api/v1/room/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH,  "/api/v1/room/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/room/**").authenticated()
                        .anyRequest().authenticated()
        );

        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        http.sessionManagement(sessionConfig -> {
            sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.csrf(AbstractHttpConfigurer::disable); // CSRF 보호 기능이 끎 REST API에서는 일반적으로 CSRF 보호가 필요하지 않음


        // 필터 순서: XSS Filter → JWT Filter → UsernamePasswordAuthenticationFilter
        // 1. XSS 필터를 가장 먼저 추가 (사용자 입력 검증)
        http.addFilterBefore(xssFilter, UsernamePasswordAuthenticationFilter.class);

        // 2. JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 추가 (인증 처리)
        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

        // exception authenticationEntryPoint 추가 401 에러 처리
        http.exceptionHandling(exception -> {
            log.info("here");
            exception.authenticationEntryPoint(customAuthenticationEntryPoint);
        });
        // exceptionHandler, 접근 거부 핸들러 추가
        http.exceptionHandling(config -> {
            config.accessDeniedHandler(customAccessDeniedHandler);
        });
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 출처 설정 (모든 출처 허용)
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));  // localhost:3000 -> 허용
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"
        ));
        // 허용할 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // 허용할 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // 자격 증명 허용 설정 (쿠키 등)
        configuration.setAllowCredentials(true);
        // content-disposition 허용 설정 -> excel 파일 다운로드시, 제목노출을 위해 필요!
        configuration.setExposedHeaders(Arrays.asList("Content-Disposition"));
        // CORS 설정을 특정 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

