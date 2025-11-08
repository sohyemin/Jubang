package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.dto.ReservationResponse;
import HelloWorld.Jubang.domain.reservation.entity.Reservation;
import HelloWorld.Jubang.domain.reservation.repository.ReservationRepository;
import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import HelloWorld.Jubang.domain.room.service.RegisterService;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import HelloWorld.Jubang.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static HelloWorld.Jubang.exception.ErrorCode.ROOM_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;

    @Override
    public void makeReservation(ReservationRequestDto requestDto, String email) {
        User user = getUser(email);
        Room room = getEntity(requestDto.getRoomId());

        Reservation saveRsv = reservationRepository.save(Reservation.from(requestDto, user, room));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReservationResponse> getAllReservations_ForHost(String email, Pageable pageable) {

        return reservationRepository.findAllByHostId(email, pageable)
                .map(ReservationResponse::toDto);

    }

    @Override
    public ReservationDetailResponse getReservation_ForHost(String email) {
        return null;
    }

    @Override
    public List<ReservationDetailResponse> getAllReservations(String email) {
        return List.of();
    }

    @Override
    public ReservationDetailResponse getReservation(String email) {
        return null;
    }

    @Override
    public void canselReservation(Long rsvId, String email) {

    }

    @Override
    public void denyReservation(Long rsvId, String email) {

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
