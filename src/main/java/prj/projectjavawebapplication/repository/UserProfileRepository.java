package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.model.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<UserProfile> findByUserId(Long userId);
}
