package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.dto.BookingDTO;
import prj.projectjavawebapplication.model.entity.Ticket;
import prj.projectjavawebapplication.model.entity.User;

import java.util.List;

public interface TicketService {
    List<Ticket> bookTicket(
            BookingDTO dto,
            User user
    );
    Ticket findByCodeAndPhone(
            String ticketCode,
            String phone
    );
    Ticket findById(Long id);
    List<Ticket> getMyTickets(Long userId);
    List<Ticket> getPendingTickets();
    void confirmTicket(Long id);
    void cancelTicket(Long id);
    List<Ticket> getAllTickets();
}