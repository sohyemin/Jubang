package HelloWorld.Jubang.domain.reservation.dto;

import HelloWorld.Jubang.domain.reservation.entity.Reservation;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {


    private long rsvId;

    private String roomName;

    private String status;   //예약 상태

    // 일정
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static ReservationResponse toDto(Reservation reservation) {
        return ReservationResponse.builder()
                .roomName(reservation.getRoom().getRoomName())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .build();
    }
}
