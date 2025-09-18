package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;

public interface UserService {

    // 가입
    void join(JoinRequestDto joinRequestDto);

}
