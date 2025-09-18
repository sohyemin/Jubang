package HelloWorld.Jubang.domain.user.controller;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;
import HelloWorld.Jubang.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public void join(@Valid @RequestBody JoinRequestDto joinRequestDto){
        log.info("Join request : {}", joinRequestDto);
        userService.join(joinRequestDto);
    }
}
