package forwarding.agent.service.impl;

import forwarding.agent.api.exceptions.BadRequestException;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.mapper.AuthenticationUserMapper;
import forwarding.agent.util.AuthenticationTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private AuthenticationUserMapper authenticationUserMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnAuthenticationDtoWhenFindByEmailAndPassword() {
        User user = AuthenticationTestData.createUserForAuthentication();
        AuthenticationRequestDto authenticationRequestDto = AuthenticationTestData.createCredentialsForUserWithRoleUserOnly();
        AuthenticationUserDto authenticationUserDto = AuthenticationTestData.createAuthenticationUserDto();

        when(userRepository.findByEmail(authenticationRequestDto.email())).thenReturn(user);
        when(passwordEncoder.matches(authenticationRequestDto.password(), user.getPassword())).thenReturn(true);
        when(authenticationUserMapper.fromEntityToAuthenticationDto(user)).thenReturn(authenticationUserDto);

        AuthenticationUserDto result = authenticationService.findByEmailAndPassword(authenticationRequestDto);

        assertNotNull(result);
        assertEquals(authenticationRequestDto.email(), result.email());
    }

    @Test
    void shouldReturnNullWhenFindByEmailIsNull() {
        AuthenticationRequestDto authenticationRequestDto = AuthenticationTestData.createCredentialsForUserWithRoleUserOnly();

        when(userRepository.findByEmail(authenticationRequestDto.email())).thenReturn(null);

        assertThrows(BadRequestException.class, () ->
                authenticationService.findByEmailAndPassword(authenticationRequestDto));
    }


    @Test
    void shouldReturnNullWhenPasswordIsNotRight() {
        User user = AuthenticationTestData.createUserForAuthentication();
        AuthenticationRequestDto authenticationRequestDto = AuthenticationTestData.createCredentialsForUserWithRoleUserOnly();

        when(userRepository.findByEmail(authenticationRequestDto.email())).thenReturn(user);
        when(passwordEncoder.matches(authenticationRequestDto.password(), user.getPassword())).thenReturn(false);

        assertThrows(BadRequestException.class, () ->
                authenticationService.findByEmailAndPassword(authenticationRequestDto));
    }


}
