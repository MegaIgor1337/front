package forwarding.agent.api.controllers;

import forwarding.agent.security.jwt.JwtTokenProvider;
import forwarding.agent.service.AuthenticationService;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication Controller", description = "API for working with authentication")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        log.info("User started authorize {}", requestDto.email());
        AuthenticationUserDto user = authenticationService.findByEmailAndPassword(requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("email", user.email());
        response.put("token", jwtTokenProvider.createToken(user));
        return ResponseEntity.ok(response);
    }
}
