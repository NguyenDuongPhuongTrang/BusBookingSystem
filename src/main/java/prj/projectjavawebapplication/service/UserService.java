package prj.projectjavawebapplication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import prj.projectjavawebapplication.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> searchUsers(String keyword);
    void toggleUserStatus(Long id);
    Page<User> searchUsers(String keyword, Pageable pageable);
}
