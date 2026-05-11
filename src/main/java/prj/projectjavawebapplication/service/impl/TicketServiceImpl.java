package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.projectjavawebapplication.enums.SeatStatus;
import prj.projectjavawebapplication.enums.TicketStatus;
import prj.projectjavawebapplication.model.dto.BookingDTO;
import prj.projectjavawebapplication.model.entity.*;
import prj.projectjavawebapplication.repository.SeatRepository;
import prj.projectjavawebapplication.repository.TicketRepository;
import prj.projectjavawebapplication.repository.UserProfileRepository;
import prj.projectjavawebapplication.service.TicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public List<Ticket> bookTicket(BookingDTO dto, User user) {

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("No profile"));

        List<Seat> seats = seatRepository.findAllById(dto.getSeatIds());

        if (seats.isEmpty()) {
            throw new RuntimeException("Không có ghế nào được chọn");
        }

        LocalDateTime now = LocalDateTime.now();

        for (Seat seat : seats) {

            // ❌ không phải ghế đang pending
            if (seat.getStatus() != SeatStatus.PENDING) {
                throw new RuntimeException("Ghế " + seat.getSeatNumber() + " không hợp lệ");
            }

            // ❌ hết hạn giữ
            if (seat.getHoldUntil() == null || seat.getHoldUntil().isBefore(now)) {
                throw new RuntimeException("Ghế " + seat.getSeatNumber() + " đã hết giữ");
            }

            // ❌ không phải người giữ
            if (seat.getHoldByUser() == null ||
                    !seat.getHoldByUser().getId().equals(user.getId())) {
                throw new RuntimeException("Ghế " + seat.getSeatNumber() + " không thuộc về bạn");
            }
        }

        BigDecimal total = seats.stream()
                .map(s -> s.getTrip().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Ticket ticket = Ticket.builder()
                .ticketCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .user(user)
                .customerName(profile.getFullName())
                .phone(profile.getPhone())
                .email(profile.getEmail())
                .trip(seats.get(0).getTrip())
                .seats(seats)
                .totalPrice(total)
                .status(TicketStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        ticketRepository.save(ticket);

        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.BOOKED);
            seat.setHoldByUser(null);
            seat.setHoldUntil(null);
        }

        seatRepository.saveAll(seats);

        return List.of(ticket);
    }

    @Override
    public Ticket findByCodeAndPhone(String ticketCode, String phone) {
        return ticketRepository.findByTicketCodeAndPhone(ticketCode, phone)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));
    }

    @Override
    public List<Ticket> getMyTickets(Long userId) {
        return ticketRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Ticket> getPendingTickets() {
        return ticketRepository.findByStatusOrderByCreatedAtDesc(TicketStatus.PENDING);
    }

    @Override
    @Transactional
    public void confirmTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow();

        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new RuntimeException("Vé không hợp lệ");
        }

        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void cancelTicket(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow();

        ticket.setStatus(TicketStatus.CANCELLED);

        for (Seat seat : ticket.getSeats()) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setHoldByUser(null);
            seat.setHoldUntil(null);
        }

        seatRepository.saveAll(ticket.getSeats());
        ticketRepository.save(ticket);
    }
}