package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj.projectjavawebapplication.model.dto.HoldSeatDTO;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.service.SeatService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/trips/{tripId}/seats")
    public String seats(@PathVariable Long tripId,
                        Model model,
                        HttpSession session) {

        User currentUser = (User) session.getAttribute("USER");

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("seats", seatService.getSeatsByTrip(tripId));
        model.addAttribute("tripId", tripId);
        model.addAttribute("currentUserId", currentUser.getId());

        return "seat-map";
    }

    @PostMapping("/seats/hold")
    @ResponseBody
    public ResponseEntity<?> holdSeats(@RequestBody HoldSeatDTO request, HttpSession session) {
        User currentUser = (User) session.getAttribute("USER");

        if (currentUser == null) {
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");
        }

        try {
            seatService.holdSeats(request.getSeatIds(), currentUser.getId());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}