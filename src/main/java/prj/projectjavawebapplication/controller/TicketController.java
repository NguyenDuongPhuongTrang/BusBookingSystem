package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import prj.projectjavawebapplication.model.dto.BookingDTO;
import prj.projectjavawebapplication.model.entity.Seat;
import prj.projectjavawebapplication.model.entity.Ticket;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.model.entity.UserProfile;
import prj.projectjavawebapplication.repository.UserProfileRepository;
import prj.projectjavawebapplication.service.SeatService;
import prj.projectjavawebapplication.service.TicketService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final SeatService seatService;
    private final TicketService ticketService;
    private final UserProfileRepository userProfileRepository;

    /*
     * FORM BOOKING
     */

    @PostMapping("/booking")
    public String bookingForm(
            @RequestParam List<Long> seatIds,
            HttpSession session,
            Model model
    ) {

        /*
         * GET USER
         */

        User user = (User) session.getAttribute("USER");

        /*
         * HOLD ALL SEATS
         */

        seatService.holdSeats(
                seatIds,
                user.getId()
        );

        /*
         * PROFILE
         */

        UserProfile profile = userProfileRepository
                .findByUserId(user.getId())
                .orElseThrow();

        /*
         * GET SEATS
         */

        List<Seat> seats =
                seatService.findAllByIds(seatIds);

        /*
         * TOTAL PRICE
         */

        BigDecimal total = seats.stream()
                .map(s -> s.getTrip().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /*
         * DTO
         */

        BookingDTO dto = new BookingDTO();

        dto.setSeatIds(seatIds);

        /*
         * MODEL
         */

        model.addAttribute("bookingDTO", dto);

        model.addAttribute("profile", profile);

        model.addAttribute("seats", seats);

        model.addAttribute("total", total);

        return "ticket/booking";
    }

    /*
     * BOOKING
     */

    @PostMapping("/confirm")
    public String booking(
            @Valid
            @ModelAttribute("bookingDTO")
            BookingDTO dto,

            BindingResult result,

            HttpSession session,

            Model model
    ) {

        User user = (User) session.getAttribute("USER");

        List<Seat> seats = seatService.findAllByIds(
                dto.getSeatIds()
        );

        if (result.hasErrors()) {

            model.addAttribute("seats", seats);

            return "ticket/booking";
        }

        List<Ticket> tickets = ticketService
                .bookTicket(dto, user);

        return "redirect:/tickets/success/"
                + tickets.get(0).getId();
    }

    /*
     * SUCCESS
     */

    @GetMapping("/success/{id}")
    public String success(
            @PathVariable Long id,
            Model model
    ) {

        Ticket ticket =
                ticketService.findById(id);

        model.addAttribute(
                "ticket",
                ticket
        );

        return "ticket/success";
    }

    @GetMapping("/my-tickets")
    public String myTickets(
            HttpSession session,
            Model model
    ) {

        User user =
                (User) session.getAttribute("USER");

        model.addAttribute(
                "tickets",
                ticketService.getMyTickets(
                        user.getId()
                )
        );

        return "ticket/my-tickets";
    }
}