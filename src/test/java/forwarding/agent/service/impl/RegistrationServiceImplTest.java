package forwarding.agent.service.impl;

import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import forwarding.agent.service.mapper.AuthenticationUserMapper;
import forwarding.agent.service.mapper.RegistrationUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static forwarding.agent.util.AuthenticationTestData.createAuthenticationUserDto;
import static forwarding.agent.util.AuthenticationTestData.createRegistrationRequestDtoWithValidParams;
import static forwarding.agent.util.UserTestData.createUserWithId;
import static forwarding.agent.util.UserTestData.createUserWithoutId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RegistrationUserMapper registrationUserMapper;
    @Mock
    private AuthenticationUserMapper authenticationUserMapper;
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void shouldReturnAuthenticationUserDtoWhenRegistrationSuccess() {
        RegistrationRequestDto requestDto = createRegistrationRequestDtoWithValidParams();
        User user = createUserWithoutId();
        User savedUser = createUserWithId();
        AuthenticationUserDto authenticationUserDto = createAuthenticationUserDto();

        when(registrationUserMapper.fromRequestDtoToEntity(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(authenticationUserMapper.fromEntityToAuthenticationDto(savedUser)).thenReturn(authenticationUserDto);

        AuthenticationUserDto result = registrationService.registrationUser(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.email(), result.email());
        assertTrue(result.roleNames().contains(RoleNameEnum.USER));
    }
}
