package forwarding.agent.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsUserExistsValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUserExists {
    String message() default "Invalid user id: user does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
