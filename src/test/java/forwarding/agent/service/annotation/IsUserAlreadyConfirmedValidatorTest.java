package forwarding.agent.service.annotation;

import forwarding.agent.api.exceptions.UserAlreadyConfirmedException;
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
public class IsUserAlreadyConfirmedValidatorTest {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private IsUserAlreadyConfirmedValidator isUserAlreadyConfirmedValidator;

    @Test
    void shouldThrowExceptionWhenUserAlreadyConfirmed() {
        when(userService.isUserConfirmed(USER_ID)).thenReturn(true);

        assertThrows(UserAlreadyConfirmedException.class, () ->
                isUserAlreadyConfirmedValidator.isValid(USER_ID, null));
    }

    @Test
    void shouldReturnFalseWhenUserHaveNotConfirmed() {
        when(userService.isUserConfirmed(USER_ID)).thenReturn(false);

        boolean result = isUserAlreadyConfirmedValidator.isValid(USER_ID, null);

        assertTrue(result);
    }
}
