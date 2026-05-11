package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.projectjavawebapplication.enums.SeatStatus;
import prj.projectjavawebapplication.model.entity.Seat;
import prj.projectjavawebapplication.model.entity.Trip;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.repository.SeatRepository;
import prj.projectjavawebapplication.repository.TripRepository;
import prj.projectjavawebapplication.repository.UserRepository;
import prj.projectjavawebapplication.service.SeatService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    // =========================
    // GET SEATS
    // =========================
    @Override
    public List<Seat> getSeatsByTrip(Long tripId) {

        releaseExpiredSeats();

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow();

        return seatRepository.findByTripOrderBySeatNumber(trip);
    }

    @Override
    public Seat findById(Long id) {

        releaseExpiredSeats();

        return seatRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy ghế")
                );
    }

    @Override
    public List<Seat> findAllByIds(List<Long> ids) {

        releaseExpiredSeats();

        return seatRepository.findAllById(ids);
    }

    // =========================
    // HOLD SEATS (FIXED)
    // =========================
    @Override
    @Transactional
    public void holdSeats(List<Long> seatIds, Long userId) {
        releaseExpiredSeats();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.isEmpty()) {
            throw new RuntimeException("Không có ghế nào được chọn");
        }

        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.BOOKED) {
                throw new RuntimeException("Ghế " + seat.getSeatNumber() + " đã được đặt");
            }

            // Ghế đang được người khác giữ
            if (seat.getStatus() == SeatStatus.PENDING
                    && seat.getHoldByUser() != null
                    && !seat.getHoldByUser().getId().equals(userId)
                    && seat.getHoldUntil() != null
                    && seat.getHoldUntil().isAfter(LocalDateTime.now())) {

                throw new RuntimeException("Ghế " + seat.getSeatNumber() + " đang được người khác giữ");
            }

            // === HOLD HOẶC EXTEND ===
            seat.setStatus(SeatStatus.PENDING);
            seat.setHoldByUser(user);
            seat.setHoldUntil(LocalDateTime.now().plusMinutes(10));
        }

        seatRepository.saveAll(seats);
    }

    // =========================
    // AUTO RELEASE EXPIRED
    // =========================
    private void releaseExpiredSeats() {

        List<Seat> expiredSeats =
                seatRepository.findByStatusAndHoldUntilBefore(
                        SeatStatus.PENDING,
                        LocalDateTime.now()
                );

        for (Seat seat : expiredSeats) {

            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setHoldUntil(null);
            seat.setHoldByUser(null);
        }

        if (!expiredSeats.isEmpty()) {
            seatRepository.saveAll(expiredSeats);
        }
    }
}
