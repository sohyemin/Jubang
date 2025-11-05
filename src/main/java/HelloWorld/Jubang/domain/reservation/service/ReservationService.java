package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;

import java.util.List;

public interface ReservationService {

    // 예약
    void makeReservation(ReservationRequestDto requestDto);

    // 예약 정보 - 호스트
    List<ReservationDetailResponse> getAllReservations_ForHost();
    ReservationDetailResponse getReservation_ForHost();

    // 예약 정보 - 사용자
    List<ReservationDetailResponse> getAllReservations();
    ReservationDetailResponse getReservation();


    // 예약 취소
    void canselReservation(Long rsvId, String email);

    // 예약 거절
    void denyReservation(Long rsvId, String email);
}
