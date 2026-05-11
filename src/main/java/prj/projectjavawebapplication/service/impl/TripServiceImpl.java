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

    public List<Trip> searchTripsById(Long fromId, Long toId, LocalDate date) {
        return tripRepository.findByRouteOriginIdAndRouteDestinationIdAndStatusIn(
                fromId, toId,
                List.of(TripStatus.OPEN, TripStatus.FULL),
                date
        );
    }

    public List<Trip> searchTripsWithoutDate(Long fromId, Long toId) {
        return tripRepository.findByRouteOriginIdAndRouteDestinationIdAndStatusIn(
                fromId, toId,
                List.of(TripStatus.OPEN, TripStatus.FULL)
        );
    }

    public List<Trip> findAll() {
        return tripRepository.findByStatusIn(
                List.of(TripStatus.OPEN, TripStatus.FULL)
        );
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

        Driver driver = bus.getDriver();

        Trip trip = Trip.builder()
                .route(route)
                .bus(bus)
                .driver(bus.getDriver())
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
    public void update(Long id, TripDTO dto) {

        Trip trip = findById(id);

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow();

        Bus bus = busRepository.findById(dto.getBusId())
                .orElseThrow();

        trip.setRoute(route);
        trip.setBus(bus);
        trip.setDriver(bus.getDriver());
        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setPrice(dto.getPrice());

        tripRepository.save(trip);
    }

    @Override
    public void updateStatus(Long id, TripStatus status) {
        Trip trip = findById(id);
        trip.setStatus(status);
        tripRepository.save(trip);
    }
}