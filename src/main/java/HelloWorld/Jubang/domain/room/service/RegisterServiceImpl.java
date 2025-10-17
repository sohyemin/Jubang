package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomDetailResponse;
import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{

    private final RegisterRepository registerRepository;

    @Override
    public void insertRoom(RegisterRequestDto registerRequestDto) {

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
}
