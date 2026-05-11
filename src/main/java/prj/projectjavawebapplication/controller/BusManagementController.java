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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.projectjavawebapplication.model.dto.BusDTO;
import prj.projectjavawebapplication.model.entity.Bus;
import prj.projectjavawebapplication.model.entity.Driver;
import prj.projectjavawebapplication.repository.BusRepository;
import prj.projectjavawebapplication.repository.DriverRepository;
import prj.projectjavawebapplication.service.BusService;

@Controller
@RequestMapping("/admin/buses")
@RequiredArgsConstructor
public class BusManagementController {

    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final BusService busService;

    // LIST + PAGINATION
    @GetMapping
    public String buses(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Bus> busPage = busRepository.findAll(pageable);
        model.addAttribute("busPage", busPage);
        return "admin/buses/list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("busDTO", new BusDTO());
        model.addAttribute("drivers", driverRepository.findAll());
        return "admin/buses/create";
    }

    // CREATE
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("busDTO") BusDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // CHECK BIỂN SỐ XE
        if (busRepository.existsByLicensePlate(dto.getLicensePlate())) {
            result.rejectValue("licensePlate", "error.busDTO", "Biển số xe đã tồn tại");
        }

        // VALIDATE
        if (result.hasErrors()) {
            model.addAttribute("drivers", driverRepository.findAll());
            return "admin/buses/create";
        }

        // TÌM TÀI XẾ
        Driver driver = driverRepository.findById(dto.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        // TẠO XE
        Bus bus = new Bus();

        bus.setLicensePlate(dto.getLicensePlate());
        bus.setBusType(dto.getBusType());
        bus.setTotalSeats(dto.getTotalSeats());
        bus.setCompanyName(dto.getCompanyName());
        bus.setDriver(driver);

        busService.save(bus);

        redirectAttributes.addFlashAttribute("success", "Thêm xe thành công!");

        return "redirect:/admin/buses";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));

        BusDTO dto = new BusDTO();

        dto.setId(bus.getId());
        dto.setLicensePlate(bus.getLicensePlate());
        dto.setBusType(bus.getBusType());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setCompanyName(bus.getCompanyName());

        if (bus.getDriver() != null) {
            dto.setDriverId(bus.getDriver().getId());
        }

        model.addAttribute("busDTO", dto);
        model.addAttribute("drivers", driverRepository.findAll());
        return "admin/buses/edit";
    }

    // UPDATE
    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("busDTO") BusDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // CHECK BIỂN SỐ XE
        if (busRepository.existsByLicensePlateAndIdNot(dto.getLicensePlate(), dto.getId())) {
            result.rejectValue("licensePlate", "error.busDTO", "Biển số xe đã tồn tại");
        }

        // VALIDATE
        if (result.hasErrors()) {
            model.addAttribute("drivers", driverRepository.findAll());
            return "admin/buses/edit";
        }

        // TÌM XE
        Bus bus = busRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));

        // TÌM TÀI XẾ
        Driver driver = driverRepository.findById(dto.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        // UPDATE
        bus.setLicensePlate(dto.getLicensePlate());
        bus.setBusType(dto.getBusType());
        bus.setTotalSeats(dto.getTotalSeats());
        bus.setCompanyName(dto.getCompanyName());

        bus.setDriver(driver);

        busService.save(bus);

        redirectAttributes.addFlashAttribute("success", "Cập nhật xe thành công!");

        return "redirect:/admin/buses";
    }

    // KHOÁ/MỞ
    @GetMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Bus bus = busService.findById(id);
        bus.setActive(!bus.getActive());
        busService.save(bus);
        redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái xe thành công");
        return "redirect:/admin/buses";
    }
}