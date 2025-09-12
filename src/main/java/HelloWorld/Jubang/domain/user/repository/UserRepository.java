package HelloWorld.Jubang.domain.user.repository;

import HelloWorld.Jubang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
