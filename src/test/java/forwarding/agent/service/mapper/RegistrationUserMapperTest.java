package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.RoleRepository;
import forwarding.agent.service.dto.RegistrationRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static forwarding.agent.util.AuthenticationTestData.ENCODED_PASSWORD;
import static forwarding.agent.util.AuthenticationTestData.createRegistrationRequestDtoWithValidParams;
import static forwarding.agent.util.RoleTestData.createRole;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationUserMapperTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RegistrationUserMapper registrationUserMapper;

    @Test
    void shouldReturnUserWhenMapFromRegistrationDto() {
        RegistrationRequestDto requestDto = createRegistrationRequestDtoWithValidParams();

        when(roleRepository.findByRoleName(RoleNameEnum.UNCONFIRMED_USER)).thenReturn(createRole());
        when(passwordEncoder.encode(requestDto.password())).thenReturn(ENCODED_PASSWORD);

        User result = registrationUserMapper.fromRequestDtoToEntity(requestDto);

        assertEquals(requestDto.firstName(), result.getFirstName());
        assertEquals(requestDto.email(), result.getEmail());
        assertEquals(requestDto.fatherName(), result.getFatherName());
        assertEquals(requestDto.lastName(), result.getLastName());
        assertNotNull(result.getPassword());
    }
}
