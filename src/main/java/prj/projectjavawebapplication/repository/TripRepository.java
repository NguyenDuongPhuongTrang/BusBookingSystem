package prj.projectjavawebapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prj.projectjavawebapplication.enums.TripStatus;
import prj.projectjavawebapplication.model.entity.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository
        extends JpaRepository<Trip, Long> {

    @Query("""
                SELECT t
                FROM Trip t
                WHERE t.route.origin.id = :fromId
                AND t.route.destination.id = :toId
                AND t.departureTime >= :start
                AND t.departureTime < :end
            """)
    List<Trip> searchTripsById(
            Long fromId,
            Long toId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
                SELECT t
                FROM Trip t
                WHERE t.route.origin.id = :fromId
                AND t.route.destination.id = :toId
            """)
    List<Trip> searchTripsWithoutDate(
            Long fromId,
            Long toId
    );

    Page<Trip> findByRouteOriginNameContainingIgnoreCaseOrRouteDestinationNameContainingIgnoreCase(
            String origin,
            String destination,
            Pageable pageable
    );

    List<Trip> findByStatusIn(List<TripStatus> statuses);

    List<Trip> findByRouteOriginIdAndRouteDestinationIdAndStatusIn(
            Long originId, Long destinationId, List<TripStatus> statuses
    );

    @Query("SELECT t FROM Trip t WHERE t.route.origin.id = :fromId " +
            "AND t.route.destination.id = :toId " +
            "AND t.status IN :statuses " +
            "AND CAST(t.departureTime AS date) = :date")
    List<Trip> findByRouteOriginIdAndRouteDestinationIdAndStatusIn(
            Long fromId, Long toId, List<TripStatus> statuses, LocalDate date
    );
}