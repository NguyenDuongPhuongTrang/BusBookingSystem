package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.projectjavawebapplication.enums.SeatStatus;
import prj.projectjavawebapplication.enums.TripStatus;
import prj.projectjavawebapplication.model.dto.TripDTO;
import prj.projectjavawebapplication.model.entity.*;
import prj.projectjavawebapplication.repository.*;
import prj.projectjavawebapplication.service.TripService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl
        implements TripService {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final SeatRepository seatRepository;

    @Override
    public List<Trip> searchTripsById(
            Long fromId,
            Long toId,
            LocalDate date
    ) {
        return tripRepository.searchTripsById(
                fromId,
                toId,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public List<Trip> searchTripsWithoutDate(
            Long fromId,
            Long toId
    ) {

        return tripRepository.searchTripsWithoutDate(
                fromId,
                toId
        );
    }

    @Override
    public List<Trip> findAll() {

        return tripRepository.findAll();
    }

    @Override
    public Trip findById(Long id) {

        return tripRepository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void create(TripDTO dto) {

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow();

        Bus bus = busRepository.findById(dto.getBusId())
                .orElseThrow();

        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow();

        Trip trip = Trip.builder()
                .route(route)
                .bus(bus)
                .driver(driver)
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .price(dto.getPrice())
                .status(TripStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        tripRepository.save(trip);

        for (int i = 1; i <= bus.getTotalSeats(); i++) {
            Seat seat = Seat.builder()
                    .trip(trip)
                    .seatNumber("A" + i)
                    .floor(1)
                    .status(SeatStatus.AVAILABLE)
                    .build();

            seatRepository.save(seat);
        }
    }

    @Override
    public void update(
            Long id,
            TripDTO dto
    ) {

        Trip trip = findById(id);

        Route route = routeRepository.findById(
                dto.getRouteId()
        ).orElseThrow();

        Bus bus = busRepository.findById(
                dto.getBusId()
        ).orElseThrow();

        Driver driver = driverRepository.findById(
                dto.getDriverId()
        ).orElseThrow();

        trip.setRoute(route);
        trip.setBus(bus);
        trip.setDriver(driver);
        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setPrice(dto.getPrice());

        tripRepository.save(trip);
    }

    @Override
    public void updateStatus(
            Long id,
            TripStatus status
    ) {

        Trip trip = findById(id);

        trip.setStatus(status);

        tripRepository.save(trip);
    }
}