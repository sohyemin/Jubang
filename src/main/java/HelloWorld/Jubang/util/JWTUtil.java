package HelloWorld.Jubang.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JWTUtil {

    private final JwtProps jwtProps;

    // 토큰 생성
    public String generateToken(Map<String, Objects> valueMap, int min){
        SecretKey key = null;
        try {
            key= Keys.
        }
    }
}
