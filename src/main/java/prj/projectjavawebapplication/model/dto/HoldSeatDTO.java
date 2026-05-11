package prj.projectjavawebapplication.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HoldSeatDTO {
    private List<Long> seatIds;
}
