package HelloWorld.Jubang.domain.reservation.dto;

import HelloWorld.Jubang.domain.reservation.enums.PayMethod;
import HelloWorld.Jubang.domain.room.entity.Room;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private Long roomId;

    // 예약자 정보
    private String rsvName;
    private String rsvHP;
    private int people;

    // 일정
    private LocalDate checkIn;
    private LocalDate checkOut;

    // 금액
    private int totalPay;

    // 결제 수단 / 상태
    private String payMethod;

}
