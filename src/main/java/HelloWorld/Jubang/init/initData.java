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

            registerService.insertRoom(requestDto, "user1@test.com");

            for (int i = 1; i < 5; i++) {
                log.info("reservation...");
                ReservationRequestDto rsvRequest = ReservationRequestDto.builder()
                        .roomId(1L)
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
