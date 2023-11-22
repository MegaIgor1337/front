package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.EmailAlreadyExistsException;
import forwarding.agent.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsEmailAlreadyExistsValidator implements ConstraintValidator<IsEmailAlreadyExists, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (userService.isUserExistsByEmail(email)) {
            throw new EmailAlreadyExistsException(String.format("User with email %s already exists", email));
        } else {
            return true;
        }
    }
}
