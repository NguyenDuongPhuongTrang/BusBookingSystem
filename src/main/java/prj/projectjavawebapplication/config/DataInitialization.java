package prj.projectjavawebapplication.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.repository.UserRepository;

import java.time.LocalDateTime;

@Configuration
public class DataInitialization {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByUsername("admin").isEmpty()) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setPasswordHash(encoder.encode("123456"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                admin.setCreatedAt(LocalDateTime.now());

                userRepo.save(admin);
            }
        };
    }
}