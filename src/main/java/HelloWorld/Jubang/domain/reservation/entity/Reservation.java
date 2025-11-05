package HelloWorld.Jubang.domain.reservation.entity;

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

}

