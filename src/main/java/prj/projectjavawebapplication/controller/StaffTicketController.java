package prj.projectjavawebapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj.projectjavawebapplication.service.TicketService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/tickets")
public class StaffTicketController {

    private final TicketService ticketService;

    /*
     * LIST PENDING
     */

    @GetMapping
    public String pendingTickets(Model model){

        model.addAttribute(
                "tickets",
                ticketService.getPendingTickets()
        );

        return "staff/ticket-list";
    }

    /*
     * CONFIRM
     */

    @PostMapping("/{id}/confirm")
    public String confirm(
            @PathVariable Long id
    ){

        ticketService.confirmTicket(id);

        return "redirect:/staff/tickets";
    }

    /*
     * CANCEL
     */

    @PostMapping("/{id}/cancel")
    public String cancel(
            @PathVariable Long id
    ){

        ticketService.cancelTicket(id);

        return "redirect:/staff/tickets";
    }
}