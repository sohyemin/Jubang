package HelloWorld.Jubang.domain.room.controller;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomListResponse;
import HelloWorld.Jubang.domain.room.service.RegisterService;
import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.security.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    // 등록
    @PostMapping("/register")
    public Response<?> registerRoom(@Valid @RequestBody RegisterRequestDto requestDto,
                                    @AuthenticationPrincipal UserDTO userDTO){
        registerService.insertRoom(requestDto, userDTO.getEmail());
        return Response.success("등록되었습니다.");
    }

    // 방 목록 보기
    @GetMapping("/list")
    public Response<List<RoomListResponse>> list(){
        return null;
    }
}
