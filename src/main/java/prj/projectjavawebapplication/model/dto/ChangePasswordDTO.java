package prj.projectjavawebapplication.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import prj.projectjavawebapplication.validation.PasswordConstraint;
import prj.projectjavawebapplication.validation.confirmpass.ConfirmPasswordConstraint;
import prj.projectjavawebapplication.validation.confirmpass.PasswordConfirmation;

@Getter
@Setter
@ConfirmPasswordConstraint
public class ChangePasswordDTO implements PasswordConfirmation {

    @NotBlank(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;

    @PasswordConstraint
    private String newPassword;

    @NotBlank(message = "Nhập lại mật khẩu")
    private String confirmPassword;

    @Override
    public String getPassword() {
        return newPassword;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }
}