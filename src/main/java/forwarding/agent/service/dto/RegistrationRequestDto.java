package forwarding.agent.service.dto;

import forwarding.agent.service.annotation.IsEmailAlreadyExists;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import static forwarding.agent.service.util.ValidateConstants.REGEXP_VALIDATE_EMAIL;
import static forwarding.agent.service.util.ValidateConstants.REGEXP_VALIDATE_PASSWORD;

@Builder
public record RegistrationRequestDto(
        @NotNull(message = "First name can not be null")
        @NotEmpty(message = "First name can not be empty")
        @Length(min = 2, max = 100, message = "First name must be longer than 2 symbols and shorter than 100")
        @Schema(defaultValue = "Igor", description = "User first name")
        String firstName,
        @NotNull(message = "Last name can not be null")
        @NotEmpty(message = "Last name can not be empty")
        @Length(min = 2, max = 100, message = "Last name must be longer than 2 symbols and shorter than 100")
        @Schema(defaultValue = "Yakubovich", description = "User last name")
        String lastName,
        @NotEmpty(message = "Father name can not be empty")
        @Length(min = 2, max = 100, message = "Father name must be longer than 2 symbols and shorter than 100")
        @Schema(defaultValue = "Sergeevich", description = "User first name")
        String fatherName,
        @NotNull(message = "Password can not be null")
        @NotEmpty(message = "Password can not be empty")
        @Length(min = 4, max = 100, message = "Password must be longer than 4 symbols and shorter than 100")
        @Pattern(regexp = REGEXP_VALIDATE_PASSWORD, message = "Invalid password address. " +
                                                              "Password must consist of min 4 symbols, " +
                                                              "one of them is latin symbol, " +
                                                              "one of them is number")
        @Schema(defaultValue = "sdfF32d", description = "User password")
        String password,
        @NotNull(message = "Email can not be null")
        @NotEmpty(message = "Email can not be empty")
        @IsEmailAlreadyExists
        @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid e-mail address")
        @Schema(defaultValue = "user@gmail.com", description = "Email address")
        String email
) {
}
