package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.util.AuthenticationTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationUserMapperTest {
    @InjectMocks
    private final AuthenticationUserMapper authenticationUserMapper = Mappers.getMapper(AuthenticationUserMapper.class);
    @Test
    void shouldReturnAuthenticationUserDto() {
        User user = AuthenticationTestData.createUserForAuthentication();

        AuthenticationUserDto result = authenticationUserMapper.fromEntityToAuthenticationDto(user);

        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getRoles().size(), result.roleNames().size());
    }
}
