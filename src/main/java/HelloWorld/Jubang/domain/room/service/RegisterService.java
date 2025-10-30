package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomModifyRequest;
import HelloWorld.Jubang.security.UserDTO;

import java.util.List;

public interface RegisterService {

    /** CREATE **/
    //방 등록
    void insertRoom(RegisterRequestDto registerRequestDto, String email);

    /** READ **/
    //방 전체 목록
    List<RoomDetailResponse> listAllRoom();
    //방 디테일
    RoomDetailResponse detailRoom(long roomNo);

    /** UPDATE **/
    //방 정보 수정
    long modify(long roomId, RoomModifyRequest request, String email);

    /** DELETE **/
    //방 삭제
    void deleteRoom(long roomId, String email);

}
