package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.dto.LoginDTO;
import prj.projectjavawebapplication.model.dto.RegisterDTO;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.service.AuthService;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("req", new LoginDTO());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("req") LoginDTO dto,
                        BindingResult result,
                        HttpSession session,
                        RedirectAttributes ra) {

        if (result.hasErrors()) return "auth/login";

        try {
            User user = authService.login(dto);
            session.setAttribute("USER", user);

            return switch (user.getRole()) {
                case ADMIN -> "redirect:/admin";
                case STAFF -> "redirect:/staff/tickets";
                default -> "redirect:/home";
            };

        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("req", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("req") RegisterDTO dto,
                           BindingResult result,
                           RedirectAttributes ra) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error", "Mật khẩu không khớp");
        }

        if (result.hasErrors()) return "auth/register";

        try {
            authService.register(dto);
            ra.addFlashAttribute("success", "Đăng ký thành công");
            return "redirect:/login";
        } catch (Exception e) {
            result.rejectValue("username", "error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
