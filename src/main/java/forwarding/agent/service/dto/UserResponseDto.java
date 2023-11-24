package forwarding.agent.service.dto;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String fatherName,
        String email
) {
}
