package forwarding.agent.service.impl;

import forwarding.agent.api.exceptions.BadRequestException;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.AuthenticationService;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import forwarding.agent.service.mapper.AuthenticationUserMapper;
import forwarding.agent.service.mapper.RegistrationUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationUserMapper authenticationUserMapper;
    private final RegistrationUserMapper registrationMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.email());
        if (user != null && passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            AuthenticationUserDto authenticationUserDto = authenticationUserMapper.fromEntityToAuthenticationDto(user);
            log.info("findByEmailAndPassword - authenticationUserDto: {} found by email: {}", authenticationUserDto,
                    authenticationUserDto.email());
            return authenticationUserDto;
        } else {
            log.info("findByEmailAndPassword - Invalid username or password");
            throw new BadRequestException("Wrong email or password");
        }
    }


}
