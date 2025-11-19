package HelloWorld.Jubang.init;

import HelloWorld.Jubang.domain.reservation.dto.ReservationRequestDto;
import HelloWorld.Jubang.domain.reservation.service.ReservationService;
import HelloWorld.Jubang.domain.room.dto.RegisterRequestDto;
import HelloWorld.Jubang.domain.room.dto.RoomImageRequest;
import HelloWorld.Jubang.domain.room.service.RegisterService;
import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import HelloWorld.Jubang.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class initData {

    private final UserRepository userRepository;
    private final UserService userService;

    private final RegisterService registerService;

    private final ReservationService reservationService;

    @Bean
    public CommandLineRunner init(){
        return args -> {
            log.info("Initializing data...");

            // 초기 데이터 삽입 예시
            if (userRepository.count() == 0) {
                log.info("No members found, initializing default members...");
                JoinRequestDTO joinRequest = JoinRequestDTO.builder()
                        .email("admin@test.com")
                        .name("admin")
                        .password("admin1234")
                        .build();
                userService.join(joinRequest);

                for (int i = 0; i < 5; i++) {
                    JoinRequestDTO userRequest = JoinRequestDTO.builder()
                            .email("user" + i + "@test.com")
                            .name("user" + i)
                            .password("1234")
                            .build();
                    userService.join(userRequest);
                }
            }

            RegisterRequestDto requestDto = RegisterRequestDto.builder()
                    .roomName("강남 오피스텔")
                    .description("깔끔한 신축 원룸")
                    .zipcode("06211")
                    .address1("서울 강남구 테헤란로 123")
                    .address2("302호")
                    .roomRCount(1)
                    .roomBCount(1)
                    .roomLCount(0)
                    .roomKCount(1)
                    .basicOptions(List.of("에어컨", "세탁기"))
                    .additionalOptions(List.of("전자레인지"))
                    .building("오피스텔")
                    .elevator(true)
                    .parking(false)
                    .leastPay(10)
                    .deposit(1000)
                    .rent(75)
                    .administrationFee(5)
                    .payOption("MONTHLY")
                    .discount(0)
                    .discountFee(0)
                    .images(List.of(
                            new RoomImageRequest("room1.jpg", 0),
                            new RoomImageRequest("room2.jpg", 1)
                    ))
                    .build();

            RegisterRequestDto requestDto2 = RegisterRequestDto.builder()
                    .roomName("홍대 근처 원룸")
                    .description("역세권 도보 3분, 리모델링 완료된 깔끔한 원룸")
                    .zipcode("04000")
                    .address1("서울 마포구 와우산로 89")
                    .address2("202호")
                    .roomRCount(1)
                    .roomBCount(1)
                    .roomLCount(0)
                    .roomKCount(1)
                    .basicOptions(List.of("에어컨", "세탁기", "전자레인지"))
                    .additionalOptions(List.of("정수기", "인터넷 포함"))
                    .building("원룸")
                    .elevator(false)
                    .parking(false)
                    .leastPay(6)
                    .deposit(500)
                    .rent(55)
                    .administrationFee(3)
                    .payOption("MONTHLY")
                    .discount(5)
                    .discountFee(3)
                    .images(List.of(
                            new RoomImageRequest("hongdae1.jpg", 0),
                            new RoomImageRequest("hongdae2.jpg", 1)
                    ))
                    .build();

            RegisterRequestDto requestDto3 = RegisterRequestDto.builder()
                    .roomName("해운대 오션뷰 투룸")
                    .description("바다가 보이는 고층 오션뷰, 테라스 포함된 넓은 투룸")
                    .zipcode("48094")
                    .address1("부산 해운대구 우동 1408")
                    .address2("37층")
                    .roomRCount(2)
                    .roomBCount(1)
                    .roomLCount(1)
                    .roomKCount(1)
                    .basicOptions(List.of("에어컨", "빌트인 가전", "세탁기"))
                    .additionalOptions(List.of("테라스", "시스템 에어컨"))
                    .building("아파트")
                    .elevator(true)
                    .parking(true)
                    .leastPay(12)
                    .deposit(2000)
                    .rent(120)
                    .administrationFee(10)
                    .payOption("MONTHLY")
                    .discount(0)
                    .discountFee(0)
                    .images(List.of(
                            new RoomImageRequest("haeundae1.jpg", 0),
                            new RoomImageRequest("haeundae2.jpg", 1)
                    ))
                    .build();

            RegisterRequestDto requestDto4 = RegisterRequestDto.builder()
                    .roomName("대구 경대 앞 고시원")
                    .description("학생 전용, 최소 비용으로 생활 가능한 고시원 방")
                    .zipcode("41566")
                    .address1("대구 북구 대현로 23")
                    .address2("5층 502호")
                    .roomRCount(1)
                    .roomBCount(0)
                    .roomLCount(0)
                    .roomKCount(0)
                    .basicOptions(List.of("책상", "침대", "공용샤워실"))
                    .additionalOptions(List.of("와이파이 무료", "식사 제공"))
                    .building("고시원")
                    .elevator(true)
                    .parking(false)
                    .leastPay(1)
                    .deposit(50)
                    .rent(25)
                    .administrationFee(0)
                    .payOption("MONTHLY")
                    .discount(10)
                    .discountFee(2)
                    .images(List.of(
                            new RoomImageRequest("gosi1.jpg", 0),
                            new RoomImageRequest("gosi2.jpg", 1)
                    ))
                    .build();

            registerService.insertRoom(requestDto, "user1@test.com");
            registerService.insertRoom(requestDto2, "user2@test.com");
            registerService.insertRoom(requestDto3, "user3@test.com");
            registerService.insertRoom(requestDto4, "user4@test.com");

            for (int i = 1; i < 5; i++) {
                log.info("reservation...");
                ReservationRequestDto rsvRequest = ReservationRequestDto.builder()
                        .roomId((long) i)
                        .rsvName("홍길동")
                        .rsvHP("010-1234-5678")
                        .people(2)
                        .checkIn(LocalDate.parse("2025-11-10"))
                        .checkOut(LocalDate.parse("2025-11-12"))
                        .totalPay(150000)
                        .payMethod("CARD")
                        .build();
                reservationService.makeReservation(rsvRequest, "user"+i+"@test.com");
            }
        };
    }
}
