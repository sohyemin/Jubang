package HelloWorld.Jubang.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static void setTokenCookie(HttpServletResponse response, String name, String value, long mins) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/") // CORS 설정, 모든 경로에서 접근 가능, localhost:8080/api 에서 path: "/api"
                .httpOnly(true) // XSS 방지, JS에서 쿠키값을 읽는 것을 불가, XSS란?
                .secure(true) // HTTPS, SSL 설정
                .sameSite("None") // CORS 설정, 모든 도메인에서 접근 가능, None: 모든 도메인에서 접근 가능, Lax: 일부 도메인에서만 접근 가능, Strict: 도메인에서만 접근 가능
                .maxAge(mins + 60) // maxAge 설정 ( 초 )
                .build();
    }
}
