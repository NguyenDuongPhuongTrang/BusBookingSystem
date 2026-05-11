package prj.projectjavawebapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.projectjavawebapplication.model.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}