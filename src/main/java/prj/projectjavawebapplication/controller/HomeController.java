package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj.projectjavawebapplication.model.entity.Trip;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.service.LocationService;
import prj.projectjavawebapplication.service.TripService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TripService tripService;
    private final LocationService locationService;
    boolean searched = false;

    @GetMapping("/home")
    public String home(HttpSession session,
                       Model model,
                       @RequestParam(required = false) Long fromId,
                       @RequestParam(required = false) Long toId,
                       @RequestParam(required = false) String date) {

        User user = (User) session.getAttribute("USER");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("locations", locationService.findAll());

        List<Trip> trips = tripService.findAll();

        boolean searched = false;

        if ((fromId != null && toId == null) || (fromId == null && toId != null)) {
            model.addAttribute("error", "Vui lòng chọn đầy đủ điểm đi và điểm đến");
            model.addAttribute("trips", trips);
            model.addAttribute("searched", false);

            return "home";
        }

        // SEARCH
        if (fromId != null && toId != null) {
            searched = true;
            if (date != null && !date.isBlank()) {
                trips = tripService.searchTripsById(fromId, toId, LocalDate.parse(date));
            }
            else {
                trips = tripService.searchTripsWithoutDate(fromId, toId);
            }
        }

        model.addAttribute("searched", searched);
        model.addAttribute("trips", trips);

        return "home";
    }
}