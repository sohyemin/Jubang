package HelloWorld.Jubang.security.service;

import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.props.JwtProps;
import HelloWorld.Jubang.security.repository.TokenRepository;
import HelloWorld.Jubang.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        claims.put("name", user.getname());

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
                .name(user.getname())
                .roles(roleNames)
                .accessToken(accessToken)
                .refreshToken(refreshToken) // 모바일 클라이언트를 위해 refreshToken도 반환 (웹은 쿠키 사용)
                .build();
    }
}
