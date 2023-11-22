package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.Role;
import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.AuthenticationUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public interface AuthenticationUserMapper {

    @Mapping(target = "roleNames", expression = "java(rolesToRoleNames(user.getRoles()))")
    AuthenticationUserDto fromEntityToAuthenticationDto(User user);

    default Set<RoleNameEnum> rolesToRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }
}
