package prj.projectjavawebapplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import prj.projectjavawebapplication.enums.TripStatus;
import prj.projectjavawebapplication.model.dto.TripDTO;
import prj.projectjavawebapplication.model.entity.Trip;
import prj.projectjavawebapplication.repository.TripRepository;
import prj.projectjavawebapplication.service.*;

@Controller
@RequestMapping("/admin/trips")
@RequiredArgsConstructor
public class TripManagementController {

    private final TripRepository tripRepository;
    private final TripService tripService;
    private final RouteService routeService;
    private final BusService busService;
    private final DriverService driverService;

    /*
     * =========================
     * LIST + PAGINATION
     * =========================
     */

    @GetMapping
    public String trips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Trip> tripPage;

        if (keyword != null && !keyword.trim().isEmpty()) {

            tripPage = tripRepository
                    .findByRouteOriginNameContainingIgnoreCaseOrRouteDestinationNameContainingIgnoreCase(
                            keyword,
                            keyword,
                            pageable
                    );

        } else {

            tripPage = tripRepository.findAll(pageable);
        }

        model.addAttribute("tripPage", tripPage);
        model.addAttribute("keyword", keyword);

        return "admin/trip/list";
    }

    /*
     * =========================
     * CREATE
     * =========================
     */

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "tripDTO",
                new TripDTO()
        );

        loadData(model);

        return "admin/trip/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid
            @ModelAttribute("tripDTO")
            TripDTO dto,

            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {

            loadData(model);

            return "admin/trip/create";
        }

        tripService.create(dto);

        return "redirect:/admin/trips";
    }

    /*
     * =========================
     * EDIT
     * =========================
     */

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model
    ) {

        Trip trip = tripService.findById(id);

        TripDTO dto = TripDTO.builder()
                .routeId(trip.getRoute().getId())
                .busId(trip.getBus().getId())
                .driverId(trip.getDriver().getId())
                .departureTime(trip.getDepartureTime())
                .arrivalTime(trip.getArrivalTime())
                .price(trip.getPrice())
                .build();

        model.addAttribute("tripDTO", dto);
        model.addAttribute("tripId", id);

        loadData(model);

        return "admin/trip/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,

            @Valid
            @ModelAttribute("tripDTO")
            TripDTO dto,

            BindingResult result,

            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute("tripId", id);

            loadData(model);

            return "admin/trip/edit";
        }

        tripService.update(id, dto);

        return "redirect:/admin/trips";
    }

    /*
     * =========================
     * STATUS
     * =========================
     */

    @GetMapping("/status/{id}")
    public String statusForm(
            @PathVariable Long id,
            Model model
    ) {

        Trip trip = tripService.findById(id);

        model.addAttribute("trip", trip);

        model.addAttribute(
                "statuses",
                TripStatus.values()
        );

        return "admin/trip/status";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(
            @PathVariable Long id,

            @RequestParam TripStatus status
    ) {

        tripService.updateStatus(
                id,
                status
        );

        return "redirect:/admin/trips";
    }

    /*
     * =========================
     * LOAD DATA
     * =========================
     */

    private void loadData(Model model) {

        model.addAttribute(
                "routes",
                routeService.findAll()
        );

        model.addAttribute(
                "buses",
                busService.findAll()
        );

        model.addAttribute(
                "drivers",
                driverService.findAll()
        );
    }
}