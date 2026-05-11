package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.dto.CreateUserDTO;
import prj.projectjavawebapplication.model.dto.LoginDTO;
import prj.projectjavawebapplication.model.dto.RegisterDTO;
import prj.projectjavawebapplication.model.entity.User;

public interface AuthService {
    void register(RegisterDTO dto);
    User login(LoginDTO dto);
    void createByAdmin(CreateUserDTO dto);
}