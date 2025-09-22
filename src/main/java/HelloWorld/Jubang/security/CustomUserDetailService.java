package HelloWorld.Jubang.security;

import HelloWorld.Jubang.domain.user.entity.User;
import HelloWorld.Jubang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername: username: {}", username);

        User user = userRepository.getWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("미존재하는 사용자 email: " + username));

        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getUserRoleList().stream().map(Enum::name).toList()
        );

        log.info("loadUserByUsername result userDTO: {}", userDTO);

        return userDTO;
    }
}
