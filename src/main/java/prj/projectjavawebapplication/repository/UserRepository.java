package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRoleNot(Role role);

    List<User> findByRoleNotAndUsernameContainingIgnoreCase(
            Role role,
            String keyword
    );
}