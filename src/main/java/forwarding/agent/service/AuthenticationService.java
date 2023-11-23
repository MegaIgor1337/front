package forwarding.agent.service;

import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.AuthenticationUserDto;

public interface AuthenticationService {
    AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto);
}
