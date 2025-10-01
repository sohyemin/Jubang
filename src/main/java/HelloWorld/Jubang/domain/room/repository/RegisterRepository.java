package HelloWorld.Jubang.domain.room.repository;

import HelloWorld.Jubang.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Room, Long> {
}
