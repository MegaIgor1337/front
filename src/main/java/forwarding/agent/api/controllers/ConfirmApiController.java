package forwarding.agent.api.controllers;

import forwarding.agent.service.UserService;
import forwarding.agent.service.annotation.IsUserAlreadyConfirmed;
import forwarding.agent.service.annotation.IsUserExists;
import forwarding.agent.service.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Confirmed Controller", description = "API for working with confirming user")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/confirm")
public class ConfirmApiController {
    private final UserService userService;

    @Operation(summary = "Confirm user")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> confirmUser(@IsUserExists
                                            @IsUserAlreadyConfirmed
                                            @PathVariable("userId")
                                            Long userId) {
        log.info("Admin started confirming user with id {}", userId);
        UserResponseDto responseDto = userService.confirmUser(userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Get list of unconfirmed users")
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> get() {
        log.info("Admin is getting list of unconfirmed users");
        List<UserResponseDto> users = userService.getUnconfirmedUsers();
        return ResponseEntity.ok(users);
    }
}
