package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDto;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;

import HelloWorld.Jubang.security.UserDTO;
import HelloWorld.Jubang.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

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

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> login(String email, String password) {
        return Map.of();
    }


}
