package forwarding.agent.service.dto;

import forwarding.agent.persistense.entity.RoleNameEnum;
import lombok.Builder;

import java.io.Serializable;
import java.util.Set;

@Builder
public record AuthenticationUserDto(
        String email,
        Set<RoleNameEnum> roleNames
) implements Serializable {
}