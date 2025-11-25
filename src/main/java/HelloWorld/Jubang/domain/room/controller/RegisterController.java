package HelloWorld.Jubang.domain.room.controller;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomListResponse;
import HelloWorld.Jubang.domain.room.dto.RoomModifyRequest;
import HelloWorld.Jubang.domain.room.service.RegisterService;
import HelloWorld.Jubang.dto.PageRequestDTO;
import HelloWorld.Jubang.dto.PageResponseDTO;
import HelloWorld.Jubang.dto.Response;
import HelloWorld.Jubang.security.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    // 등록
    @PostMapping("")
    public Response<?> registerRoom(@Valid @RequestBody RegisterRequestDto requestDto,
                                    @AuthenticationPrincipal UserDTO userDTO){
        registerService.insertRoom(requestDto, userDTO.getEmail());
        return Response.success("등록되었습니다.");
    }

    /**
        페이징 처리
     */
    // 방 목록 보기
    @GetMapping("")
    public Response<PageResponseDTO<RoomListResponse>> list(
            @Valid PageRequestDTO pageRequestDTO){
        return Response.success(registerService.listAllRoom(pageRequestDTO));
    }

    // 방 디테일 보기
    @GetMapping("/{roomId}")
    public Response<RoomDetailResponse> roomDetail(@PathVariable Long roomId){
        return Response.success(registerService.detailRoom(roomId));
    }

    // 수정
    @PutMapping("/{roomId}")
    public Response<String> modify(@PathVariable Long roomId, @Valid RoomModifyRequest request,
                                   @AuthenticationPrincipal UserDTO userDTO) {
        log.info("Room modify request: {}", request);
        log.info("UserDto: {}", userDTO);
        Long result = registerService.modify(roomId, request, userDTO.getEmail());
        return Response.success("수정 되었습니다. roomId: " + result);
    }

    // 삭제
    @DeleteMapping("/{roomId}")
    public Response<?> deleteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDTO userDTO) {
        registerService.deleteRoom(roomId, userDTO.getEmail());

        return Response.success("Delete user successful");
    }
}