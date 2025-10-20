package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.dto.RoomImageRequest;
import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{

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
    public RoomDetailResponse detailRoom(int roomNo) {
        return null;
    }

    @Override
    public void updateRoom(RegisterRequestDto registerRequestDto) {

    }

    @Override
    public void deleteRoom(int roomNo) {

    }

    /**
     * 유저 조회
     */
    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 없습니다."));
    }
}
