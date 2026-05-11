package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.entity.Driver;
import prj.projectjavawebapplication.repository.DriverRepository;
import prj.projectjavawebapplication.service.DriverService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public Page<Driver> search(
            String keyword,
            Pageable pageable
    ) {

        if (keyword == null || keyword.isBlank()) {

            return driverRepository.findAll(pageable);
        }

        return driverRepository
                .findByFullNameContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }

    @Override
    public List<Driver> findAll() {

        return driverRepository.findAll();
    }

    @Override
    public Driver findById(Long id) {

        return driverRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tài xế"));
    }

    @Override
    public Driver save(Driver driver) {

        return driverRepository.save(driver);
    }

    @Override
    public boolean existsByPhone(String phone) {

        return driverRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByPhoneAndNotId(
            String phone,
            Long id
    ) {

        return driverRepository.findAll()
                .stream()
                .anyMatch(d ->
                        !d.getId().equals(id)
                                &&
                                d.getPhone().equals(phone));
    }
}