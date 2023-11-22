package forwarding.agent.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsEmailAlreadyExistsValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEmailAlreadyExists {
    String message() default "Invalid email: email exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
