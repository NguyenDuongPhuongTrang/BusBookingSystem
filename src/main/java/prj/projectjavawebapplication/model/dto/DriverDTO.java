package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import prj.projectjavawebapplication.validation.PhoneConstraint;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDTO {

    @NotBlank(message = "Tên tài xế không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneConstraint
    private String phone;

    @NotBlank(message = "Số bằng lái không được để trống")
    private String licenseNumber;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
}
