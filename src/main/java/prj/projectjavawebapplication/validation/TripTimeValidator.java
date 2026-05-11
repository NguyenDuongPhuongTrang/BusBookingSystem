package prj.projectjavawebapplication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prj.projectjavawebapplication.model.dto.TripDTO;

public class TripTimeValidator
        implements ConstraintValidator<TripTimeConstraint, TripDTO> {

    @Override
    public boolean isValid(
            TripDTO dto,
            ConstraintValidatorContext context
    ) {

        if (
                dto.getDepartureTime() == null
                        ||
                        dto.getArrivalTime() == null
        ) {
            return true;
        }

        return dto.getArrivalTime()
                .isAfter(dto.getDepartureTime());
    }
}
