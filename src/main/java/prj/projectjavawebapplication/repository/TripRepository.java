package prj.projectjavawebapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prj.projectjavawebapplication.model.entity.Trip;

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
}