package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;

public interface UserService {

    // 가입
    void join(JoinRequestDTO joinRequestDto);

    // 로그인
    LoginResponseDTO login(String email, String password);

    // 탈퇴
    void deleteUser(String email);

    // 이메일 확인
    boolean checkEmail(String email);
}
