package forwarding.agent.service.impl;

import forwarding.agent.api.exceptions.UserNotFoundException;
import forwarding.agent.persistense.entity.Role;
import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.RoleRepository;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.dto.UserResponseDto;
import forwarding.agent.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static forwarding.agent.util.RoleTestData.craeteUncomfiredRole;
import static forwarding.agent.util.RoleTestData.createRole;
import static forwarding.agent.util.UserTestData.*;
import static forwarding.agent.util.UserTestData.createListOfUnconfirmedUsers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnTrueWhenUserWithEmailExists() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        boolean result = userService.isUserExistsByEmail(EMAIL);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUserWithEmailDoesNotExist() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);

        boolean result = userService.isUserExistsByEmail(EMAIL);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenUserExistsById() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);

        boolean result = userService.isUserExistsById(USER_ID);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUserExistsById() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);

        boolean result = userService.isUserExistsById(USER_ID);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenUserIsNotConfirmed() {
        User user = craeteUnconfirmedUser();
        Role role = craeteUncomfiredRole();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER))
                .thenReturn(role);

        boolean result = userService.isUserConfirmed(USER_ID);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenUserIsConfirmed() {
        User user = createUserWithId();
        Role role = craeteUncomfiredRole();

        when(roleRepository.findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER))
                .thenReturn(role);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean result = userService.isUserConfirmed(USER_ID);

        assertTrue(result);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCheckIfUserConfirmed() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.isUserConfirmed(USER_ID);
        });
    }
    @Test
    void shouldConfirmUserByIdCorrectly() {
        User unconfirmedUser = craeteUnconfirmedUser();
        Role unconfirmedRole = craeteUncomfiredRole();
        Role userRole = createRole();
        User updatedUser = createUserWithId();
        UserResponseDto userResponseDto = createUserResponseDto();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(unconfirmedUser));
        when(roleRepository.findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER)).thenReturn(unconfirmedRole);
        when(roleRepository.findByRoleName(RoleNameEnum.ROLE_USER)).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.fromEntityToResponseDto(updatedUser)).thenReturn(userResponseDto);

        UserResponseDto result = userService.confirmUser(USER_ID);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(USER_ID);
        verify(roleRepository, times(1)).findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER);
        verify(roleRepository, times(1)).findByRoleName(RoleNameEnum.ROLE_USER);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnListOfUnconfirmedUsers() {
        List<User> unconfirmedUsers = createListOfUnconfirmedUsers();
        List<UserResponseDto> unconfirmedUsersDto = createListOfUnconfirmedUsersDto();

        when(userRepository.findAllByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER)).thenReturn(unconfirmedUsers);
        when(userMapper.fromEntityToResponseDto(any(User.class))).thenReturn(unconfirmedUsersDto.get(0),
                unconfirmedUsersDto.get(1));

        List<UserResponseDto> result = userService.getUnconfirmedUsers();

        assertEquals(unconfirmedUsers.size(), result.size());
        assertEquals(unconfirmedUsers.get(0).getFatherName(), result.get(0).fatherName());
        assertEquals(unconfirmedUsers.get(1).getLastName(), result.get(1).lastName());
    }
}
