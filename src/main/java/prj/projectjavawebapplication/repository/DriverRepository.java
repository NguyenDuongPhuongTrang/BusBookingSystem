package prj.projectjavawebapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.projectjavawebapplication.model.entity.Driver;

@Repository
public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    Page<Driver> findByFullNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    boolean existsByPhone(String phone);
}