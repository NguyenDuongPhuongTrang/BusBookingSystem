package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.entity.Bus;

import java.util.List;

public interface BusService {
    List<Bus> findAll();
    Bus findById(Long id);
    void save(Bus bus);
    void delete(Long id);
}