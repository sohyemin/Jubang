package HelloWorld.Jubang.domain.room.dto;

import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.entity.RoomImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailResponse {

    // 기본 정보
    String roomName;
    String description;

    // 주소
    String zipcode;
    String address1;
    String address2;

    // 상세 카운트
    int maxPeople;        // 최대 인원
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

    public static RoomDetailResponse to(Room room) {
        return RoomDetailResponse.builder()

                // 기본 정보
                .roomName(room.getRoomName())
                .description(room.getDescription())

                // 주소
                .zipcode(room.getZipcode())
                .address1(room.getAddress1())
                .address2(room.getAddress2())

                // 상세 카운트
                .maxPeople(room.getMaxPeople())
                .roomRCount(room.getRoomRCount())
                .roomBCount(room.getRoomBCount())
                .roomLCount(room.getRoomLCount())
                .roomKCount(room.getRoomKCount())

                // 옵션 (List → String)
                .basicOptions(Arrays.stream(room.getBasicOption().split(",")).toList())
                .additionalOptions(Arrays.stream(room.getAdditionalOption().split(",")).toList())

                // 건물/편의
                .building(room.getBuilding())
                .elevator(room.isInfoElevator())
                .parking(room.isInfoParking())

                // 가격
                .leastPay(room.getRoomLeastPay())
                .deposit(room.getRoomDeposit())
                .rent(room.getRoomRent())
                .administrationFee(room.getRoomAdministrationFee())
                .payOption(room.getRoomPayOption())

                // 할인
                .discount(room.getRoomDiscount())
                .discountFee(room.getRoomDiscountFee())

                // 이미지 (List<RoomImageRequest> → List<RoomImage>)
                .images(
                        room.getImageList() == null ? new ArrayList<>() :
                                room.getImageList().stream()
                                        .map(i -> new RoomImageRequest(i.getImageName(), i.getOrd()))
                                        .toList()
                )
                .build();
    }
}
