package forwarding.agent.service.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String fatherName,
        String email
) {
}
