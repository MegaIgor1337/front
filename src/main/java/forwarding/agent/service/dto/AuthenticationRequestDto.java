package forwarding.agent.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.io.Serializable;

import static forwarding.agent.service.util.ValidateConstants.REGEXP_VALIDATE_EMAIL;

@Builder
public record AuthenticationRequestDto(
        @NotBlank(message = "Email is mandatory!")
        @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid e-mail address")
        @Schema(defaultValue = "user@gmail.com", description = "Email address")
        String email,

        @NotBlank(message = "Password is mandatory!")
        @Schema(defaultValue = "2222", description = "Password")
        String password) implements Serializable {
}