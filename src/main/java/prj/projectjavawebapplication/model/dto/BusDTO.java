package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prj.projectjavawebapplication.validation.LicensePlateConstraint;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {

    private Long id;

    @NotBlank(message = "Biển số xe không được để trống")
    @LicensePlateConstraint
    private String licensePlate;

    @NotBlank(message = "Loại xe không được để trống")
    private String busType;

    @NotNull(message = "Số ghế không được để trống")
    @Min(value = 1, message = "Số ghế phải lớn hơn 0")
    private Integer totalSeats;

    @NotBlank(message = "Tên nhà xe không được để trống")
    private String companyName;

    @NotNull(message = "Vui lòng chọn tài xế")
    private Long driverId;
}
