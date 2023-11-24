package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.UserNotFoundException;
import forwarding.agent.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsUserExistsValidator implements ConstraintValidator<IsUserExists, Long> {
    private final UserService userService;
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (userService.isUserExistsById(id)) {
            return true;
        } else {
            throw new UserNotFoundException(String.format("User with id %s does not exist", id));
        }
    }
}
