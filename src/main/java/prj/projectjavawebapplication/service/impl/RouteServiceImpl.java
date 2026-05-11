package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.entity.Route;
import prj.projectjavawebapplication.repository.RouteRepository;
import prj.projectjavawebapplication.service.RouteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Override
    public List<Route> findAll() {

        return routeRepository.findAll();
    }
}
