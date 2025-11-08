package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.dto.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    // 예약
    void makeReservation(ReservationRequestDto requestDto, String email);

    // 예약 정보 - 호스트
    Page<ReservationResponse> getAllReservations_ForHost(String email, Pageable pageable);
    ReservationDetailResponse getReservation_ForHost(String email);

    // 예약 정보 - 사용자
    List<ReservationDetailResponse> getAllReservations(String email);
    ReservationDetailResponse getReservation(String email);

    // 예약 취소
    void canselReservation(Long rsvId, String email);

    // 예약 거절
    void denyReservation(Long rsvId, String email);
}
