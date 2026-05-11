package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.dto.RevenueByMonthDTO;
import prj.projectjavawebapplication.model.dto.RevenueByRouteDTO;
import prj.projectjavawebapplication.model.dto.TopTripDTO;

import java.util.List;

public interface AdminDashboardService {
    List<RevenueByMonthDTO> getRevenueByMonth();
    List<RevenueByRouteDTO> getRevenueByRoute();
    List<TopTripDTO> getTopTrips();
}