package HelloWorld.Jubang.domain.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RsvStatus {
    PENDING("예약대기"),
    CONFIRMED("예약확인"),
    COMPLETED("예약종료"),
    DENY("관리자 거절"),
    CANCELED("사용자 취소");

    private final String rsvStatus;
}
