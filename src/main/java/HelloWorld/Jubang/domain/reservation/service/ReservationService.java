package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;

import java.util.List;

public interface ReservationService {

    // 예약
    void makeReservation(ReservationRequestDto requestDto, String email);

    // 예약 정보 - 호스트
    List<ReservationDetailResponse> getAllReservations_ForHost(String email);
    ReservationDetailResponse getReservation_ForHost(String email);

    // 예약 정보 - 사용자
    List<ReservationDetailResponse> getAllReservations(String email);
    ReservationDetailResponse getReservation(String email);

    // 예약 취소
    void canselReservation(Long rsvId, String email);

    // 예약 거절
    void denyReservation(Long rsvId, String email);
}
