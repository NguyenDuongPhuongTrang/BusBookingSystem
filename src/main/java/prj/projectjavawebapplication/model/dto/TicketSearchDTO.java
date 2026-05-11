package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import prj.projectjavawebapplication.validation.PhoneConstraint;

@Getter
@Setter
public class TicketSearchDTO {

    @NotBlank(message = "Mã vé không được để trống")
    private String ticketCode;

    @NotBlank(message = "Số điện thoại không được để trống")

    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneConstraint
    private String phone;
}