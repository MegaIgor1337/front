package forwarding.agent.service.mapper;

import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.entity.UserStatusEnum;
import forwarding.agent.persistense.repository.RoleRepository;
import forwarding.agent.service.dto.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RegistrationUserMapper {
    private static final RoleNameEnum DEFAULT_ROLE_NAME = RoleNameEnum.USER;
    private static final UserStatusEnum DEFAULT_USER_STATUS = UserStatusEnum.NOT_CONFIRMED;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User fromRequestDtoToEntity(RegistrationRequestDto requestDto) {
        return User.builder()
                .email(requestDto.email())
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .fatherName(requestDto.fatherName())
                .userStatus(DEFAULT_USER_STATUS)
                .roles(Set.of(
                        roleRepository.findByRoleName(DEFAULT_ROLE_NAME)
                ))
                .password(passwordEncoder.encode(requestDto.password()))
                .build();
    }
}
