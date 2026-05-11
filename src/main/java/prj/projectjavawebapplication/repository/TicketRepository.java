package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prj.projectjavawebapplication.enums.TicketStatus;
import prj.projectjavawebapplication.model.dto.RevenueByRouteDTO;
import prj.projectjavawebapplication.model.dto.TopTripDTO;
import prj.projectjavawebapplication.model.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketCodeAndPhone(
            String ticketCode,
            String phone
    );

    List<Ticket> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<Ticket> findByStatusOrderByCreatedAtDesc(
            TicketStatus status
    );

    // Doanh thu theo tháng
    @Query(value = """
                SELECT 
                    DATE_FORMAT(t.created_at, '%Y-%m') as month,
                    SUM(t.total_price) as revenue
                FROM tickets t
                WHERE t.status = 'PAID'
                GROUP BY DATE_FORMAT(t.created_at, '%Y-%m')
                ORDER BY month
            """, nativeQuery = true)
    List<Object[]> revenueByMonth();

    // Doanh thu theo tuyến
    @Query("""
                SELECT new prj.projectjavawebapplication.model.dto.RevenueByRouteDTO(
                    CONCAT(t.trip.route.origin.name, ' → ', t.trip.route.destination.name),
                    SUM(t.totalPrice)
                )
                FROM Ticket t
                WHERE t.status = 'PAID'
                GROUP BY t.trip.route.id
            """)
    List<RevenueByRouteDTO> revenueByRoute();

    // Top 5 chuyến hot nhất
    @Query("""
                SELECT new prj.projectjavawebapplication.model.dto.TopTripDTO(
                    t.trip.id,
                    CONCAT(t.trip.route.origin.name, ' → ', t.trip.route.destination.name),
                    COUNT(t.id),
                    SUM(t.totalPrice)
                )
                FROM Ticket t
                WHERE t.status = 'PAID'
                GROUP BY t.trip.id, t.trip.route.id
                ORDER BY COUNT(t.id) DESC
            """)
    List<TopTripDTO> topTrips();

    List<Ticket> findAllByOrderByCreatedAtDesc();
}
