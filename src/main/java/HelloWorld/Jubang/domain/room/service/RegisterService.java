package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomModifyRequest;

import java.util.List;

public interface RegisterService {

    /** CREATE **/
    //방 등록
    void insertRoom(RegisterRequestDto registerRequestDto, String email);

    /** READ **/
    //방 전체 목록
    List<RoomDetailResponse> listAllRoom();
    //방 디테일
    RoomDetailResponse detailRoom(int roomNo);

    /** UPDATE **/
    //방 정보 수정
    void modify(Long roomId, RoomModifyRequest request, String email);

    /** DELETE **/
    //방 삭제
    void deleteRoom(int roomNo);

}
