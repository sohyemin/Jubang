package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(JoinRequestDto request) {

        try{
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("이미 존재하는 회원입니다!");
                    });
        } catch (Exception e){
            throw new UsernameNotFoundException("해당하는 회원은 없습니다.");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = User.from(request);
        // 회원 저장
        userRepository.save(user);
    }
}
