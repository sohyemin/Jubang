package HelloWorld.Jubang.domain.room.service;

import HelloWorld.Jubang.domain.room.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{

    private final RegisterRepository registerRepository;

}
