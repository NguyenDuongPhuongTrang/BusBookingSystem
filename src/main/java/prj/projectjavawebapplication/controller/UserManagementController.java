package prj.projectjavawebapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.service.UserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;

    // LIST USER
    @GetMapping
    public String listUsers(@RequestParam(required = false) String keyword,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<User> userPage = userService.searchUsers(keyword, pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("userPage", userPage);
        model.addAttribute("keyword", keyword);

        return "admin/users/list";
    }

    // KHOÁ / MỞ KHOÁ
    @GetMapping("/toggle/{id}")
    public String toggleUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.toggleUserStatus(id);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái tài khoản thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
}