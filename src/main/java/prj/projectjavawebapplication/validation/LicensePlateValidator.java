package prj.projectjavawebapplication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LicensePlateValidator
        implements ConstraintValidator<LicensePlateConstraint, String> {

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        return value.matches(
                "^[0-9]{2}[A-Z]-[0-9]{3}\\.[0-9]{2}$"
        );
    }
}
