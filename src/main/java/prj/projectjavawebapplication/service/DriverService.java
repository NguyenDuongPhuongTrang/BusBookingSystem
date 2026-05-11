package prj.projectjavawebapplication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import prj.projectjavawebapplication.model.entity.Driver;

import java.util.List;

public interface DriverService {
    Page<Driver> search(String keyword, Pageable pageable);
    List<Driver> findAll();
    Driver findById(Long id);
    Driver save(Driver driver);
    boolean existsByPhone(String phone);
    boolean existsByPhoneAndNotId(String phone, Long id);
}