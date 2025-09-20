package HelloWorld.Jubang.security.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String ACCESS_TOKEN_PREFIX = "access:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh:";
    private static final String BLACKLIST_PREFIX = "blacklist:";

    /**
     * 리프레시 토큰 저장
     */
    public void saveRefreshToken(String email, String refreshToken, long expirationTime) {
        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.opsForValue().set(key, refreshToken, expirationTime, TimeUnit.MINUTES);
        log.info("리프레시 토큰 저장 email {}, refreshToken: {}", email, refreshToken);
    }
}
