package HelloWorld.Jubang.domain.reservation.service;

import HelloWorld.Jubang.domain.reservation.dto.ReservationDetailResponse;
import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Override
    public void makeReservation(ReservationRequestDto requestDto) {

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
}
