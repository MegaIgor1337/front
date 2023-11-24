package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public interface UserMapper {
    UserResponseDto fromEntityToResponseDto(User user);
}
