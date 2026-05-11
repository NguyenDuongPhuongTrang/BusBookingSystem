package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.entity.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByTrip(Long tripId);
    Seat findById(Long id);
    List<Seat> findAllByIds(List<Long> ids);
    void holdSeats(List<Long> seatIds, Long userId);
}