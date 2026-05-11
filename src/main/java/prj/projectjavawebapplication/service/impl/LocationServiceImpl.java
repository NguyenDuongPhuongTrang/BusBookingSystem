package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.entity.Location;
import prj.projectjavawebapplication.repository.LocationRepository;
import prj.projectjavawebapplication.service.LocationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}
