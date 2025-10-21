package HelloWorld.Jubang.security.service;

import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.props.JwtProps;
import HelloWorld.Jubang.security.repository.TokenRepository;
import HelloWorld.Jubang.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;

    /**
     * 로그인 처리 및 토큰 발급
     */
    public LoginResponseDTO issueTokens(User user) {
        //토큰에 담을 정보 준비
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());

        List<String> roleNames = user.getUserRoleList().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        claims.put("roleNames", roleNames);

        //토큰 생성
        String accessToken = jwtUtil.generateToken(claims, jwtProps.getAccessTokenExpirationPeriod());
        String refreshToken = jwtUtil.generateToken(claims, jwtProps.getRefreshTokenExpirationPeriod());

        // 리프레시 토큰을 Redis에 저장
        tokenRepository.saveRefreshToken(
                user.getEmail(),
                refreshToken,
                jwtProps.getRefreshTokenExpirationPeriod()
        );

        // 응답 DTO 생성
        return LoginResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .roles(roleNames)
                .accessToken(accessToken)
                .refreshToken(refreshToken) // 모바일 클라이언트를 위해 refreshToken도 반환 (웹은 쿠키 사용)
                .build();
    }

    /**
     * 로그아웃 처리
     */
    public void logout(String email, String accessToken) {
        // 리프레시 토큰 삭제
        tokenRepository.deleteRefreshToken(email);

        // 액세스 토큰 블랙리스트에 추가
        // 만료 시간 계산
        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            Integer exp = (Integer) claims.get("exp");
            long expirationTime = Instant.ofEpochSecond(exp).toEpochMilli();
            long remainingTime = expirationTime - System.currentTimeMillis();

            if (remainingTime > 0) {
                tokenRepository.addToBlacklist(accessToken, remainingTime);
            }
        } catch (Exception e) {
            log.error("토큰 블랙리스트(로그아웃) 추가 중 오류: {}", e.getMessage());
        }
    }

    /**
     * 토큰 블랙리스트 확인
     */
    public boolean isTokenBlacklisted(String accessToken) {
        return tokenRepository.isBlacklisted(accessToken);
    }

}
