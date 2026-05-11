package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.entity.Bus;
import prj.projectjavawebapplication.repository.BusRepository;
import prj.projectjavawebapplication.service.BusService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(Long id) {
        return busRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy xe")
        );
    }

    @Override
    public void save(Bus bus) {
        if (bus.getCreatedAt() == null) {
            bus.setCreatedAt(LocalDateTime.now());
        }
        busRepository.save(bus);
    }
}
