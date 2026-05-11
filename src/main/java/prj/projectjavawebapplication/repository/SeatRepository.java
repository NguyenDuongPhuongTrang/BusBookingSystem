package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.enums.SeatStatus;
import prj.projectjavawebapplication.model.entity.Seat;
import prj.projectjavawebapplication.model.entity.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTripOrderBySeatNumber(Trip trip);

    Optional<Seat> findByIdAndStatus(Long id, SeatStatus status);

    List<Seat> findByStatusAndHoldUntilBefore(
            SeatStatus status,
            LocalDateTime time
    );
}