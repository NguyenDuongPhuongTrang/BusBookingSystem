package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.projectjavawebapplication.model.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
