package HelloWorld.Jubang.domain.user.service;

import HelloWorld.Jubang.domain.user.dto.JoinRequestDTO;
import HelloWorld.Jubang.domain.user.dto.LoginResponseDTO;
import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;

import HelloWorld.Jubang.exception.CustomException;
import HelloWorld.Jubang.security.CustomUserDetailService;
import HelloWorld.Jubang.security.UserDTO;
import HelloWorld.Jubang.security.service.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static HelloWorld.Jubang.exception.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;
    private final TokenService tokenService;

    @Override
    public void join(JoinRequestDTO request) {


        try{
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("이미 존재하는 회원입니다!");
                    });
        } catch (Exception e) {
                throw new UsernameNotFoundException("해당하는 회원은 없습니다.");
            }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = User.from(request);
        // 회원 저장
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponseDTO login(String email, String password) {
        UserDTO userAuthDTO = (UserDTO) userDetailService.loadUserByUsername(email);
        log.info("email :{}, password :{}", email, password);

        if (!passwordEncoder.matches(password, userAuthDTO.getPassword())) {
            throw new CustomException(USER_NOT_FOUND, "비밀번호가 틀렸습니다.");
        }

        User user = this.getEntity(email);

        return tokenService.issueTokens(user);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public User getEntity(String email) {
        return userRepository.getWithRoles(email)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 회원이 없습니다. email: " + email));
    }
}
