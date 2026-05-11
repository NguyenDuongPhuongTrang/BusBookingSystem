package prj.projectjavawebapplication.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.dto.CreateUserDTO;
import prj.projectjavawebapplication.model.dto.LoginDTO;
import prj.projectjavawebapplication.model.dto.RegisterDTO;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.model.entity.UserProfile;
import prj.projectjavawebapplication.repository.UserProfileRepository;
import prj.projectjavawebapplication.repository.UserRepository;
import prj.projectjavawebapplication.service.AuthService;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final UserProfileRepository profileRepo;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepo,
                           UserProfileRepository profileRepo,
                           PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.profileRepo = profileRepo;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void register(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu không khớp");
        }

        validateUnique(dto.getUsername(), dto.getEmail(), dto.getPhone());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user.setRole(Role.PASSENGER);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        user = userRepo.save(user);

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setFullName(dto.getFullName());
        profile.setPhone(dto.getPhone());
        profile.setEmail(dto.getEmail());
        profile.setAddress(dto.getAddress());

        profileRepo.save(profile);
    }

    @Override
    public User login(LoginDTO dto) {

        User user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!encoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        if (!user.getEnabled()) {
            throw new RuntimeException("Tài khoản bị khóa");
        }

        return user;
    }

    @Override
    public void createByAdmin(CreateUserDTO dto) {
        validateUnique(dto.getUsername(), dto.getEmail(), dto.getPhone());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        user = userRepo.save(user);

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setFullName(dto.getFullName());
        profile.setPhone(dto.getPhone());
        profile.setEmail(dto.getEmail());
        profile.setAddress(dto.getAddress());

        profileRepo.save(profile);
    }

    private void validateUnique(String username, String email, String phone) {

        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        if (profileRepo.existsByEmail(email)) {
            throw new RuntimeException("Email đã tồn tại");
        }

        if (profileRepo.existsByPhone(phone)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }
    }
}
