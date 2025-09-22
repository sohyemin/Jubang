package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;

import java.util.Map;

public interface UserService {

    // 가입
    void join(JoinRequestDTO joinRequestDto);

    // 로그인
    LoginResponseDTO login(String email, String password);
}
