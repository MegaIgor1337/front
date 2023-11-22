package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.EmailAlreadyExistsException;
import forwarding.agent.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static forwarding.agent.util.UserTestData.EMAIL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsEmailAlreadyExistsValidatorTest {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private IsEmailAlreadyExistsValidator isEmailAlreadyExistsValidator;

    @Test
    void shouldReturnTrueWhenUserWithEmailDoesNotExist() {
        when(userService.isUserExistsByEmail(EMAIL)).thenReturn(false);

        boolean result = isEmailAlreadyExistsValidator.isValid(EMAIL, null);

        assertTrue(result);
    }

    @Test
    void shouldThrowExceptionWhenUserWithEmailExists() {
        when(userService.isUserExistsByEmail(EMAIL)).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () ->
                isEmailAlreadyExistsValidator.isValid(EMAIL, null));
    }
}
