package HelloWorld.Jubang.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    // 기본 정보
    String roomName;
    String description;

    // 주소
    String zipcode;
    String address1;
    String address2;

    // 상세 카운트
    int roomRCount;      // 침실
    int roomBCount;      // 화장실
    int roomLCount;      // 거실
    int roomKCount;      // 부엌

    // 옵션 (문자열 합치기 대신 리스트로)
    List<String> basicOptions;
    List<String> additionalOptions;

    // 건물/편의
    String building;
    boolean elevator;            // 엘리베이터 여부 (true/false)
    boolean parking;            // 주차 여부

    // 가격
    int leastPay;
    int deposit;
    int rent;
    int administrationFee;
    String payOption;

    // 할인
    int discount;
    int discountFee;

    // 이미지
    List<RoomImageRequest> images;

}