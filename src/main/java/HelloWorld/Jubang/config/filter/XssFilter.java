package HelloWorld.Jubang.config.filter;

import HelloWorld.Jubang.config.filter.wrapper.XssRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1) // XSS 필터를 가장 먼저 실행
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // 방 조회 GET은 XSS 필터 제외
        if ("GET".equalsIgnoreCase(request.getMethod()) && path.startsWith("/api/v1/room/")) return true;

        // 로그인, 회원가입 등 인증 관련 API는 XSS 필터 제외
        return path.startsWith("/api/v1/user/") ||
                path.startsWith("/swagger-ui/") ||
                request.getMethod().equals("OPTIONS"); // CORS preflight 제외
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Wrap the filter with the new filter.
        // Any requests to the HttpRequest or HttpResponse will go through the wrapper.
        chain.doFilter(new XssRequestWrapper(request), response);
    }
}
