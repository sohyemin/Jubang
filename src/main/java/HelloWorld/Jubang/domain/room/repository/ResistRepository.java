package HelloWorld.Jubang.domain.room.repository;

import HelloWorld.Jubang.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ResistRepository extends JpaRepository<Room, String> {

}
