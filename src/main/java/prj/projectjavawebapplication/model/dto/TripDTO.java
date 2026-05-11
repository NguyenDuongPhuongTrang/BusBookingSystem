package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import prj.projectjavawebapplication.validation.TripTimeConstraint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TripTimeConstraint
public class TripDTO {

    private Long id;

    @NotNull(message = "Vui lòng chọn tuyến đường")
    private Long routeId;

    @NotNull(message = "Vui lòng chọn xe")
    private Long busId;

    @NotNull(message = "Vui lòng chọn giờ khởi hành")
    @Future(message = "Giờ khởi hành phải ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @NotNull(message = "Vui lòng chọn giờ đến")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Giá vé không được để trống")
    @DecimalMin(
            value = "50000",
            message = "Giá vé tối thiểu là 50.000đ"
    )
    private BigDecimal price;
}