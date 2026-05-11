package prj.projectjavawebapplication.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.validation.PhoneConstraint;
import prj.projectjavawebapplication.validation.confirmpass.ConfirmPasswordConstraint;
import prj.projectjavawebapplication.validation.EmailConstraint;
import prj.projectjavawebapplication.validation.PasswordConstraint;
import prj.projectjavawebapplication.validation.confirmpass.PasswordConfirmation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfirmPasswordConstraint
public class CreateUserDTO implements PasswordConfirmation {

    @Column(unique = true)
    @NotBlank(message = "Tên người dùng không được trống")
    private String username;

    @PasswordConstraint
    private String password;

    @NotBlank(message = "Nhập lại password")
    private String confirmPassword;

    @NotBlank(message = "Họ tên không được trống")
    private String fullName;

    @Column(unique = true)
    @NotBlank(message = "Số điện thoại không được trống")
    @PhoneConstraint
    private String phone;

    @Column(unique = true)
    @NotBlank(message = "Email không được trống")
    @EmailConstraint
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotNull(message = "Bạn chưa lựa chọn vai trò")
    private Role role;
}
