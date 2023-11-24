package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.UserAlreadyConfirmedException;
import forwarding.agent.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsUserAlreadyConfirmedValidator implements ConstraintValidator<IsUserAlreadyConfirmed, Long> {
    private final UserService userService;
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (userService.isUserConfirmed(id)) {
            throw new UserAlreadyConfirmedException(String.format("User with id %s already confirmed", id));
        } else {
            return true;
        }
    }
}
