package HelloWorld.Jubang.domain.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayMethod {
    CARD("카드결제"),
    CASH("현금결제"),
    TRANSFER("계좌이체"),
    EASY_PAY("간편결제");

    private final String payMethod;
}