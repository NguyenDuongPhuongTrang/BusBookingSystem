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
import prj.projectjavawebapplication.model.dto.DriverDTO;
import prj.projectjavawebapplication.model.entity.Driver;
import prj.projectjavawebapplication.service.DriverService;

@Controller
@RequestMapping("/admin/drivers")
@RequiredArgsConstructor
public class DriverManagementController {

    private final DriverService driverService;

    /*
     * =========================
     * LIST
     * =========================
     */

    @GetMapping
    public String drivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Driver> driverPage =
                driverService.search(
                        keyword,
                        pageable
                );

        model.addAttribute(
                "driverPage",
                driverPage
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        return "admin/driver/list";
    }

    /*
     * =========================
     * CREATE
     * =========================
     */

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "driverDTO",
                new DriverDTO()
        );

        return "admin/driver/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid
            @ModelAttribute("driverDTO")
            DriverDTO dto,

            BindingResult result
    ) {

        if (driverService.existsByPhone(dto.getPhone())) {

            result.rejectValue(
                    "phone",
                    "error.phone",
                    "Số điện thoại đã tồn tại"
            );
        }

        if (result.hasErrors()) {

            return "admin/driver/create";
        }

        Driver driver = Driver.builder()
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .licenseNumber(dto.getLicenseNumber())
                .address(dto.getAddress())
                .active(true)
                .build();

        driverService.save(driver);

        return "redirect:/admin/drivers";
    }

    /*
     * =========================
     * EDIT FORM
     * =========================
     */

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model
    ) {

        Driver driver = driverService.findById(id);

        DriverDTO dto = DriverDTO.builder()
                .fullName(driver.getFullName())
                .phone(driver.getPhone())
                .licenseNumber(driver.getLicenseNumber())
                .address(driver.getAddress())
                .build();

        model.addAttribute(
                "driverDTO",
                dto
        );

        model.addAttribute(
                "driverId",
                id
        );

        return "admin/driver/edit";
    }

    /*
     * =========================
     * UPDATE
     * =========================
     */

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,

            @Valid
            @ModelAttribute("driverDTO")
            DriverDTO dto,

            BindingResult result,

            Model model
    ) {

        Driver driver = driverService.findById(id);

        boolean existsPhone =
                driverService.existsByPhoneAndNotId(
                        dto.getPhone(),
                        id
                );

        if (existsPhone) {

            result.rejectValue(
                    "phone",
                    "error.phone",
                    "Số điện thoại đã tồn tại"
            );
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "driverId",
                    id
            );

            return "admin/driver/edit";
        }

        driver.setFullName(dto.getFullName());
        driver.setPhone(dto.getPhone());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setAddress(dto.getAddress());

        driverService.save(driver);

        return "redirect:/admin/drivers";
    }

    /*
     * =========================
     * TOGGLE STATUS
     * =========================
     */

    @GetMapping("/toggle/{id}")
    public String toggleStatus(
            @PathVariable Long id
    ) {

        Driver driver = driverService.findById(id);

        driver.setActive(!driver.getActive());

        driverService.save(driver);

        return "redirect:/admin/drivers";
    }
}