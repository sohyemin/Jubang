package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomListResponse;
import HelloWorld.Jubang.domain.room.dto.RoomModifyRequest;
import HelloWorld.Jubang.dto.PageRequestDTO;
import HelloWorld.Jubang.dto.PageResponseDTO;
import HelloWorld.Jubang.security.UserDTO;

import java.util.List;

public interface RegisterService {

    /** CREATE **/
    //방 등록
    void insertRoom(RegisterRequestDto registerRequestDto, String email);

    /** READ **/
    //방 전체 목록
    PageResponseDTO<RoomListResponse> listAllRoom(PageRequestDTO requestDTO);
    //방 디테일
    RoomDetailResponse detailRoom(Long roomId);
    // host의 방 목록
    PageResponseDTO<RoomDetailResponse> listAllRoomforHost(PageRequestDTO requestDTO);

    /** UPDATE **/
    //방 정보 수정
    Long modify(Long roomId, RoomModifyRequest request, String email);

    /** DELETE **/
    //방 삭제
    void deleteRoom(Long roomId, String email);

}
