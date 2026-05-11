package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.dto.CreateUserDTO;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.service.AuthService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    public String adminHome(HttpSession session, Model model) {

        User user = (User) session.getAttribute("USER");

        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "admin/dashboard";
    }

    @GetMapping("/create-user")
    public String form(Model model) {
        model.addAttribute("req", new CreateUserDTO());
        model.addAttribute("roles", List.of(Role.STAFF, Role.PASSENGER));
        return "admin/users/create-user";
    }

    @PostMapping("/create-user")
    public String create(@Valid @ModelAttribute("req") CreateUserDTO dto,
                         BindingResult result,
                         RedirectAttributes ra) {

        if (result.hasErrors()) return "admin/users/create-user";

        try {
            authService.createByAdmin(dto);
            ra.addFlashAttribute("success", "Tạo tài khoản thành công");
            return "redirect:/admin";
        } catch (Exception e) {
            result.rejectValue("username", "error", e.getMessage());
            return "admin/users/create-user";
        }
    }
}