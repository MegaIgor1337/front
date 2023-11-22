package forwarding.agent.api.controllers;

import forwarding.agent.security.jwt.JwtTokenProvider;
import forwarding.agent.service.RegistrationService;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Tag(name = "Registration Controller", description = "API for working with registration")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/registration")
public class RegistrationApiController {
    private final JwtTokenProvider jwtTokenProvider;
    private final RegistrationService registrationService;
    @Operation(summary = "Registration user")
    @PostMapping
    public ResponseEntity<Map<String, String>> registration(@Valid @RequestBody RegistrationRequestDto requestDto) {
        AuthenticationUserDto user = registrationService.registrationUser(requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("email", user.email());
        response.put("token", jwtTokenProvider.createToken(user));
        return ResponseEntity.ok(response);
    }
}
