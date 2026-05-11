package prj.projectjavawebapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.model.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
    boolean existsByLicensePlate(String licensePlate);
    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);
    Page<Bus> findAll(Pageable pageable);
}