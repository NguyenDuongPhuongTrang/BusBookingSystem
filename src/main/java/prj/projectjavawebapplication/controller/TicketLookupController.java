package prj.projectjavawebapplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import prj.projectjavawebapplication.model.dto.TicketSearchDTO;
import prj.projectjavawebapplication.model.entity.Ticket;
import prj.projectjavawebapplication.service.TicketService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lookup")
public class TicketLookupController {

    private final TicketService ticketService;

    /*
     * FORM
     */

    @GetMapping
    public String lookupPage(
            Model model
    ) {

        model.addAttribute(
                "searchDTO",
                new TicketSearchDTO()
        );

        return "ticket/lookup";
    }

    /*
     * SEARCH
     */

    @PostMapping
    public String lookup(
            @Valid
            @ModelAttribute("searchDTO")
            TicketSearchDTO dto,

            BindingResult result,

            Model model
    ) {

        /*
         * VALIDATE
         */

        if (result.hasErrors()) {

            return "ticket/lookup";
        }

        try {

            Ticket ticket =
                    ticketService.findByCodeAndPhone(
                            dto.getTicketCode(),
                            dto.getPhone()
                    );

            model.addAttribute(
                    "ticket",
                    ticket
            );

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "ticket/lookup";
    }
}