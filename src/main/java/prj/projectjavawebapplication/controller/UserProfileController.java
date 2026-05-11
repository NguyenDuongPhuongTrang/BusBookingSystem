package prj.projectjavawebapplication.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.projectjavawebapplication.model.dto.ChangePasswordDTO;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.model.entity.UserProfile;
import prj.projectjavawebapplication.repository.UserProfileRepository;
import prj.projectjavawebapplication.repository.UserRepository;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("USER");

        UserProfile profile = profileRepository.findByUserId(user.getId())
                .orElse(new UserProfile());

        model.addAttribute("profile", profile);

        return "profile";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute UserProfile profile,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("USER");

        profile.setUser(user);
        profile.setUserId(user.getId());

        profileRepository.save(profile);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("passwordDTO", new ChangePasswordDTO());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("passwordDTO") ChangePasswordDTO dto,
                                 BindingResult result,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        User user = (User) session.getAttribute("USER");

        // 1. DTO validation lỗi (empty, confirm, length,...)
        if (result.hasErrors()) {
            model.addAttribute("passwordDTO", dto);
            return "change-password";
        }

        // 2. check mật khẩu cũ
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPasswordHash())) {
            result.rejectValue("oldPassword", null, "Mật khẩu cũ không đúng!");
            return "change-password";
        }

        // 3. check trùng mật khẩu mới = cũ (bonus nên có)
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPasswordHash())) {
            result.rejectValue("newPassword", null, "Mật khẩu mới không được trùng mật khẩu cũ!");
            return "change-password";
        }

        // 4. update password
        user.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        // 5. update session
        session.setAttribute("USER", user);

        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công!");
        return "redirect:/profile/change-password";
    }
}