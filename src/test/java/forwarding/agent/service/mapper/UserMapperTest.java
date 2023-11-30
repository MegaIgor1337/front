package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static forwarding.agent.util.UserTestData.createUserWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldReturnResponseDtoWhenMapFromEntity() {
        User user = createUserWithId();

        UserResponseDto result = userMapper.fromEntityToResponseDto(user);

        assertEquals(user.getId(), result.id());
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getFatherName(), result.fatherName());
    }
}
