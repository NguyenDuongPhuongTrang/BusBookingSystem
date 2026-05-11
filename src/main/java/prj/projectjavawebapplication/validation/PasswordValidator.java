package prj.projectjavawebapplication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Mật khẩu không được để trống")
                    .addConstraintViolation();
            return false;
        }

        if (value.length() < 6) {
            context.buildConstraintViolationWithTemplate("Mật khẩu phải có ít nhất 6 ký tự")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
