package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.entity.Reservation;
import HelloWorld.Jubang.domain.reservation.repository.ReservationRepository;
import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import HelloWorld.Jubang.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static HelloWorld.Jubang.exception.ErrorCode.ROOM_NOT_FOUND;

@Slf4j
@Service
@Transactional
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

    @Override
    public List<ReservationDetailResponse> getAllReservations_ForHost() {
        return List.of();
    }

    @Override
    public ReservationDetailResponse getReservation_ForHost() {
        return null;
    }

    @Override
    public List<ReservationDetailResponse> getAllReservations() {
        return List.of();
    }

    @Override
    public ReservationDetailResponse getReservation() {
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
