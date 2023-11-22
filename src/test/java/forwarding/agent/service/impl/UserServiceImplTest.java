package forwarding.agent.service.impl;

import forwarding.agent.persistense.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static forwarding.agent.util.UserTestData.EMAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnTrueWhenUserWithEmailExists() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        boolean result = userService.isUserExistsByEmail(EMAIL);

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenUserWithEmailDoesNotExist() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);

        boolean result = userService.isUserExistsByEmail(EMAIL);

        assertFalse(result);
    }
}
