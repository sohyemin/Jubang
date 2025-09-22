package HelloWorld.Jubang.domain.user.controller;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;
import HelloWorld.Jubang.domain.user.service.UserService;
import HelloWorld.Jubang.props.JwtProps;
import HelloWorld.Jubang.util.CookieUtil;
import HelloWorld.Jubang.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProps jwtProps;

    @PostMapping("/join")
    public void join(@Valid @RequestBody JoinRequestDTO joinRequestDto){
        log.info("Join request : {}", joinRequestDto);
        userService.join(joinRequestDto);
    }

    @PostMapping("/login")
    public void login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO,
            @RequestParam(required = false, defaultValue = "web") String clientType,
            HttpServletResponse response
    ){
        log.info("Login request: {}, clientType: {}", loginRequestDTO, clientType);
        //인증 및 토큰 발급
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // 클라이언트 타입에 따라 다른 처리
        if ("web".equals(clientType)) {
            // 웹 클라이언트인 경우 쿠키에 리프레시 토큰 저장
            CookieUtil.setTokenCookie(response, "refreshToken", loginResponseDTO.getRefreshToken(),
                    jwtProps.getRefreshTokenExpirationPeriod());
            // 응답에서는 리프레시 토큰 제거(보안상의 이유)
            loginResponseDTO.setRefreshToken(null);
        }
    }
    // 모바일 클라이언트는 응답 본문에 리프레시 토큰 포함 (이미 포함되어 있음)
}
