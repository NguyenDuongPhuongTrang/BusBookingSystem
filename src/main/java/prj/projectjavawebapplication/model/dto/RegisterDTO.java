package prj.projectjavawebapplication.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prj.projectjavawebapplication.validation.PhoneConstraint;
import prj.projectjavawebapplication.validation.confirmpass.ConfirmPasswordConstraint;
import prj.projectjavawebapplication.validation.EmailConstraint;
import prj.projectjavawebapplication.validation.PasswordConstraint;
import prj.projectjavawebapplication.validation.confirmpass.PasswordConfirmation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfirmPasswordConstraint
public class RegisterDTO implements PasswordConfirmation {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Column(unique = true)
    private String username;

    @PasswordConstraint
    private String password;

    @NotBlank(message = "Nhập lại password")
    private String confirmPassword;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @Column(unique = true)
    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneConstraint
    private String phone;

    @Column(unique = true)
    @NotBlank(message = "Email không được để trống")
    @EmailConstraint
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }
}
