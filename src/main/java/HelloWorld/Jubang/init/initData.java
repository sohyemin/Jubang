package HelloWorld.Jubang.init;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import HelloWorld.Jubang.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class initData {

    private final UserRepository userRepository;
    private final UserService userService;

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
        };
    }
}
