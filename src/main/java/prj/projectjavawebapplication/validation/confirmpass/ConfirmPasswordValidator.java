package prj.projectjavawebapplication.validation.confirmpass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator
        implements ConstraintValidator<ConfirmPasswordConstraint, PasswordConfirmation> {

    @Override
    public boolean isValid(PasswordConfirmation dto,
                           ConstraintValidatorContext context) {

        if (dto.getPassword() == null
                || dto.getConfirmPassword() == null) {
            return true;
        }

        boolean matched =
                dto.getPassword()
                        .equals(dto.getConfirmPassword());

        if (!matched) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                            "Mật khẩu xác nhận không khớp")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return matched;
    }
}

