package HelloWorld.Jubang.domain.room.entity;

import HelloWorld.Jubang.domain.BaseTimeEntity;
import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "room")
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @Column(name = "name")
    private String roomName;
    private String Description;

    // 주소 정보
    private String Zipcode;
    private String Address1;
    private String Address2;

    // 방 상세정보
    private int roomRCount; // 침실
    private int roomBCount; // 화장실
    private int roomLCount; // 거실
    private int roomKCount; // 부엌

    private String basicOption;      // 기본 옵션
    private String additionalOption; // 추가 옵션
    private String building;         //건물 정보
    private boolean infoElevator;     // 엘레베이터 여부
    private boolean infoParking;      // 주차장 여부

    // 가격 정보
    private int roomLeastPay;
    private int roomDeposit;
    private int roomRent;
    private int roomAdministrationFee;
    private String roomPayOption;

    // 할인 정보
    private int roomDiscount;
    private int roomDiscountFee;

    // 방 정보 이미지
    @ElementCollection
    @Builder.Default
    private List<RoomImage> imageList = new ArrayList<>();

    public static Room from(RegisterRequestDto dto, User user) {
        return Room.builder()
                .user(user)

                // 기본 정보
                .roomName(dto.getRoomName())
                .Description(dto.getDescription())

                // 주소
                .Zipcode(dto.getZipcode())
                .Address1(dto.getAddress1())
                .Address2(dto.getAddress2())

                // 상세 카운트
                .roomRCount(dto.getRoomRCount())
                .roomBCount(dto.getRoomBCount())
                .roomLCount(dto.getRoomLCount())
                .roomKCount(dto.getRoomKCount())

                // 옵션 (List → String)
                .basicOption(String.join(",", dto.getBasicOptions()))
                .additionalOption(String.join(",", dto.getAdditionalOptions()))

                // 건물/편의
                .building(dto.getBuilding())
                .infoElevator(dto.isElevator())
                .infoParking(dto.isParking())

                // 가격
                .roomLeastPay(dto.getLeastPay())
                .roomDeposit(dto.getDeposit())
                .roomRent(dto.getRent())
                .roomAdministrationFee(dto.getAdministrationFee())
                .roomPayOption(dto.getPayOption())

                // 할인
                .roomDiscount(dto.getDiscount())
                .roomDiscountFee(dto.getDiscountFee())

                // 이미지 (List<RoomImageRequest> → List<RoomImage>)
                .imageList(
                        dto.getImages() == null ? new ArrayList<>() :
                                dto.getImages().stream()
                                        .map(i -> new RoomImage(i.getImageName(), i.getOrd()))
                                        .toList()
                )

                .build();
    }

}

   