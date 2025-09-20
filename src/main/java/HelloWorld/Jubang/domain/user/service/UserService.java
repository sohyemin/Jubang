package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;

import java.util.Map;

public interface UserService {

    // 가입
    void join(JoinRequestDto joinRequestDto);

    // 로그인
    Map<String, Object> login(String email, String password);

}
