package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.enums.TicketStatus;
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
}
