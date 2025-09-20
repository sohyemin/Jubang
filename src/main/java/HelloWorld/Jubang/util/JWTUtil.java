package HelloWorld.Jubang.util;

import HelloWorld.Jubang.props.JwtProps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtil {

    private final JwtProps jwtProps;

    // 토큰 생성
    public String generateToken(Map<String, Object> valueMap, int min){
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(jwtProps.getSecretKey().getBytes("UTF-8"));
        }catch (Exception e){
            log.error(String.valueOf(e));
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().toInstant()))
                .signWith(key)
                .compact();
    }
}
