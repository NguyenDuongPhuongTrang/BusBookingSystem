package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.dto.RevenueByMonthDTO;
import prj.projectjavawebapplication.model.dto.RevenueByRouteDTO;
import prj.projectjavawebapplication.model.dto.TopTripDTO;
import prj.projectjavawebapplication.repository.TicketRepository;
import prj.projectjavawebapplication.service.AdminDashboardService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final TicketRepository ticketRepository;

    @Override
    public List<RevenueByMonthDTO> getRevenueByMonth() {
        return ticketRepository.revenueByMonth()
                .stream()
                .map(r -> new RevenueByMonthDTO(
                        (String) r[0],
                        (BigDecimal) r[1]
                ))
                .toList();
    }

    @Override
    public List<RevenueByRouteDTO> getRevenueByRoute() {
        return ticketRepository.revenueByRoute();
    }

    @Override
    public List<TopTripDTO> getTopTrips() {
        return ticketRepository.topTrips();
    }
}
