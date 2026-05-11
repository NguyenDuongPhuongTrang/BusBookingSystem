package prj.projectjavawebapplication.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TripTimeValidator.class)
@Documented
public @interface TripTimeConstraint {

    String message() default "Thời gian đến phải sau thời gian khởi hành";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
