package forwarding.agent.service;

import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;

public interface RegistrationService {
    AuthenticationUserDto registrationUser(RegistrationRequestDto requestDto);
}
