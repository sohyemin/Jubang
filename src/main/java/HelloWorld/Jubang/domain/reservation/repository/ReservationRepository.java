package HelloWorld.Jubang.domain.reservation.repository;

import HelloWorld.Jubang.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            select r
            from Reservation r
            join r.room room
            where room.user.email = :email
            order by r.createdAt desc
            """)
    Page<Reservation> findAllByHostId(@Param("email") String email, Pageable pageable);
}
