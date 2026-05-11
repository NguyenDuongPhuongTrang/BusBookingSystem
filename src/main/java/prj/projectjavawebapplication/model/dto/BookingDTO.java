package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingDTO {

    @NotEmpty(message = "Vui lòng chọn ghế")
    private List<Long> seatIds;
}