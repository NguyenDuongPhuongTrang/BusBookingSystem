package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> searchUsers(String keyword);
    void toggleUserStatus(Long id);
}
