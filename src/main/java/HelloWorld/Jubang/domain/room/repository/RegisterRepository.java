package HelloWorld.Jubang.domain.room.repository;

import HelloWorld.Jubang.domain.room.entity.Room;
import HelloWorld.Jubang.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Room, Long> {

    @Query("""
        select r
        from Reservation r
        join r.room room
        where room.user.id = :email
        order by r.createdAt desc 
    """)
    Page<Room> listAllRoomForHost(@Param("email") String email, Pageable pageable);
}
