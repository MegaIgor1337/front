package forwarding.agent.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsUserAlreadyConfirmedValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUserAlreadyConfirmed {
    String message() default "Invalid user id: user already confirmed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
