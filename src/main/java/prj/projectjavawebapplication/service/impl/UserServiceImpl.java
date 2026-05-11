package prj.projectjavawebapplication.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.repository.UserRepository;
import prj.projectjavawebapplication.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findByRoleNot(Role.ADMIN);
        }

        return userRepository.findByRoleNotAndUsernameContainingIgnoreCase(Role.ADMIN, keyword);
    }

    @Override
    @Transactional
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Không thể khóa ADMIN");
        }

        user.setEnabled(!user.getEnabled());
    }

    @Override
    public Page<User> searchUsers(String keyword, Pageable pageable) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findByRoleNot(Role.ADMIN, pageable);
        }

        return userRepository.findByRoleNotAndUsernameContainingIgnoreCase(
                Role.ADMIN,
                keyword,
                pageable
        );
    }
}

