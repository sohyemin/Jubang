package HelloWorld.Jubang.config.filter;

import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.security.service.TokenValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class JWTCheckFilter extends OncePerRequestFilter {

    private final TokenValidationService tokenValidationService;

    // 해당 필터로직(doFilterInternal)을 수행할지 여부를 결정하는 메서드
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("check uri: {}", path);

        // Pre-flight 요청은 필터를 타지 않도록 설정
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        // /api/member/로 시작하는 요청은 필터를 타지 않도록 설정
        if (path.startsWith("/api/v1/user/login") || path.startsWith("/api/v1/user/join")
                || path.startsWith("/api/v1/user/check-email")
                || path.startsWith("/api/v1/user/refresh") || path.startsWith("/api/v1/user/logout")

        ) {
            return true;
        }


        // /view 이미지 불러오기 api로 시작하는 요청은 필터를 타지 않도록 설정
        if (path.startsWith("/api/v1/image/")
        ) {
            return true;
        }


        if(path.startsWith("/static")) {
            return true;
        }

        // -----
        // health check
        if (path.startsWith("/health")) {
            return true;
        }

        // Swagger UI 경로 제외 설정
        if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs")) {
            return true;
        }
        // h2-console 경로 제외 설정
        if (path.startsWith("/h2-console")) {
            return true;
        }

        // /favicon.ico 경로 제외 설정
        if (path.startsWith("/favicon.ico")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("------------------JWTCheckFilter.................");
        log.info("request.getServletPath(): {}", request.getServletPath());
        log.info("..................................................");

        String autHeaderStr = request.getHeader("Authorization");
        log.info("autHeaderStr Authorization: {}", autHeaderStr);

        if ((Objects.equals(autHeaderStr, "Bearer null") || (autHeaderStr == null)) && (
                request.getServletPath().startsWith("/api/v1/room/")


        )) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = autHeaderStr.substring(7);

            // TokenValidationService를 사용하여 인증
            Authentication authentication = tokenValidationService.validateTokenAndCreateAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT Check Error: {}", e.getMessage());
            handleAuthenticationError(response, e);
        }
    }

    /**
     * 인증 오류 처리
     * @param response 응답
     * @param e 인증 오류
     * @throws IOException IO 예외
     */
    private void handleAuthenticationError(HttpServletResponse response, Exception e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = objectMapper.writeValueAsString(Response.error("ERROR_ACCESS_TOKEN"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(msg);
        printWriter.close();
    }
}
