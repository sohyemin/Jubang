package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomModifyRequest;
import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import HelloWorld.Jubang.exception.CustomException;
import HelloWorld.Jubang.security.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static HelloWorld.Jubang.exception.ErrorCode.ROOM_NOT_AUTHORIZED;
import static HelloWorld.Jubang.exception.ErrorCode.ROOM_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;

    @Override
    public void insertRoom(RegisterRequestDto dto, String email) {
        User user = getUser(email);

        Room saveRoom = registerRepository.save(Room.from(dto, user));
    }

    @Override
    public List<RoomDetailResponse> listAllRoom() {
        return List.of();
    }

    @Override
    public RoomDetailResponse detailRoom(Long roomNo) {
        return null;
    }

    @Override
    public Long modify(Long roomId, RoomModifyRequest request, String email) {

        Room room = getEntity(roomId);
        User user = getUser(email);
        // 방 등록 유저와 요청한 유저가 일치하는지 확인
        checkRoomRegisteredEqual(room, user);

//        // 파일 업로드 처리
//        // 현재 room에 수정할 이미지 파일 이름을 새로 초기화
//        List<String> modifyFileName = new ArrayList<>();
//        List<String> oldFileName = room.getImageList().stream().map(RoomImage::getImageName).toList();

        // 게시글 수정 로직

        return room.getRoomId();
    }

    @Override
    public void deleteRoom(Long roomId, String email) {
        Room room = getEntity(roomId);
        User user = getUser(email);
        // 방 등록 유저와 요청한 유저가 일치하는지 확인
        checkRoomRegisteredEqual(room, user);

        registerRepository.deleteById(roomId);
    }

    private void checkRoomRegisteredEqual(Room room, User user) {
        if (!room.getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException(ROOM_NOT_AUTHORIZED, "게시글 작성자와 요청한 사용자가 일치하지 않습니다.");
        }
    }

    /**
     * 유저 조회
     */
    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 없습니다."));
    }

    private Room getEntity(Long roomId) {
        return registerRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND, String.format("room not founded")));
    }
}