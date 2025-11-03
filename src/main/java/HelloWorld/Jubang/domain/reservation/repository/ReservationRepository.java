package HelloWorld.Jubang.domain.reservation.repository;

import HelloWorld.Jubang.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
