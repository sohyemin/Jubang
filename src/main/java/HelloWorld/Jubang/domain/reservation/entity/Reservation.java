package HelloWorld.Jubang.domain.reservation.entity;

import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.enums.PayMethod;
import HelloWorld.Jubang.domain.reservation.enums.RsvStatus;
import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long rsvId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

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
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Enumerated(EnumType.STRING)
    private RsvStatus status;   //예약 상태

    @Builder
    public static Reservation from(ReservationRequestDto dto, User user, Room room) {
        return Reservation.builder()
                .user(user)
                .room(room)
                .rsvName(dto.getRsvName())
                .rsvHP(dto.getRsvHP())
                .people(dto.getPeople())
                .checkIn(dto.getCheckIn())
                .checkOut(dto.getCheckOut())
                .totalPay(dto.getTotalPay())
                .payMethod(PayMethod.valueOf(dto.getPayMethod()))
                .status(RsvStatus.PENDING)
                .build();
    }

}