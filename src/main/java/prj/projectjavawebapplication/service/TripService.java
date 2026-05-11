package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.enums.TripStatus;
import prj.projectjavawebapplication.model.dto.TripDTO;
import prj.projectjavawebapplication.model.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripService {
    List<Trip> searchTripsById(
            Long fromId,
            Long toId,
            LocalDate date
    );

    List<Trip> searchTripsWithoutDate(
            Long fromId,
            Long toId
    );

    List<Trip> findAll();
    Trip findById(Long id);
    void create(TripDTO dto);
    void update(Long id, TripDTO dto);
    void updateStatus(Long id, TripStatus status);
}