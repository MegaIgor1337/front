package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.UserNotFoundException;
import forwarding.agent.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static forwarding.agent.util.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsUserExistsValidatorTest {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private IsUserExistsValidator isUserExistsValidator;

    @Test
    void shouldReturnTrueWhenUserExists() {
        when(userService.isUserExistsById(USER_ID)).thenReturn(true);

        boolean result = isUserExistsValidator.isValid(USER_ID, null);

        assertTrue(result);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userService.isUserExistsById(USER_ID)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
                isUserExistsValidator.isValid(USER_ID, null));
    }
}
