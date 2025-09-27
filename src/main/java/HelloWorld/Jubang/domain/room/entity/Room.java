package HelloWorld.Jubang.domain.room.entity;

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
public class Room {

    @Id
    private int roomNo;

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
    private String infoElevator;     // 엘레베이터 여부
    private String infoParking;      // 주차장 여부

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

}
