package HelloWorld.Jubang.domain.user.controller;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.service.UserService;
import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.props.JwtProps;
import HelloWorld.Jubang.security.UserDTO;
import HelloWorld.Jubang.security.service.TokenService;
import HelloWorld.Jubang.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProps jwtProps;
    private final TokenService tokenService;

    @PostMapping("/join")
    public Response<?> join(@Valid @RequestBody JoinRequestDTO joinRequestDto){
        log.info("Join request : {}", joinRequestDto);
        userService.join(joinRequestDto);
        return Response.success("등록 되었습니다. email: " + joinRequestDto.getEmail());
    }

    @PostMapping("/login")
    public Response<LoginResponseDTO> login(
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

        return Response.success(loginResponseDTO);
        // 모바일 클라이언트는 응답 본문에 리프레시 토큰 포함 (이미 포함되어 있음)
    }

    @PostMapping("/logout")
    public Response<String> logout(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false, defaultValue = "web") String clientType,
            @AuthenticationPrincipal UserDTO userDTO
            ) {
        log.info("Logout request: {}, clientType: {}", userDTO, clientType);

        //액세스 토큰 추출
        String accessToken = extractAccessToken(request);

        if (accessToken != null && userDTO != null) {
            // Redis에서 토큰 정보 삭제 및 블랙리스트 추가
            tokenService.logout(userDTO.getEmail(), accessToken);
        }

        // 웹 클라이언트인 경우 쿠키 삭제
        if ("web".equals(clientType)) {
            CookieUtil.removeTokenCookie(response, "refreshToken");
        }

        log.info("로그아웃 성공!");

        return Response.success("Logout successful");
    }

    @DeleteMapping
    public Response<?> deleteUser(@AuthenticationPrincipal UserDTO userDTO) {
        userService.deleteUser(userDTO.getEmail());

        return Response.success("Delete user successful");
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
