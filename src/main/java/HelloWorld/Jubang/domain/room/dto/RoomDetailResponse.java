package HelloWorld.Jubang.domain.room.dto;

import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.room.entity.RoomImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
                .Description(room.getDescription())

                // 주소
                .Zipcode(room.getZipcode())
                .Address1(room.getAddress1())
                .Address2(room.getAddress2())

                // 상세 카운트
                .maxPeople(room.getMaxPeople())
                .roomRCount(room.getRoomRCount())
                .roomBCount(room.getRoomBCount())
                .roomLCount(room.getRoomLCount())
                .roomKCount(room.getRoomKCount())

                // 옵션 (List → String)
                .basicOption(String.join(",", room.getBasicOptions()))
                .additionalOption(String.join(",", room.getAdditionalOptions()))

                // 건물/편의
                .building(room.getBuilding())
                .infoElevator(room.isElevator())
                .infoParking(room.isParking())

                // 가격
                .roomLeastPay(room.getLeastPay())
                .roomDeposit(room.getDeposit())
                .roomRent(room.getRent())
                .roomAdministrationFee(room.getAdministrationFee())
                .roomPayOption(room.getPayOption())

                // 할인
                .roomDiscount(room.getDiscount())
                .roomDiscountFee(room.getDiscountFee())

                // 이미지 (List<RoomImageRequest> → List<RoomImage>)
                .imageList(
                        room.getImages() == null ? new ArrayList<>() :
                                room.getImages().stream()
                                        .map(i -> new RoomImage(i.getImageName(), i.getOrd()))
                                        .toList()
                )
                .build();
    }
}
