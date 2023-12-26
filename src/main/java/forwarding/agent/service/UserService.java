package forwarding.agent.service;

import forwarding.agent.service.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    boolean isUserExistsByEmail(String email);
    UserResponseDto confirmUser(Long id);
    boolean isUserExistsById(Long id);
    boolean isUserConfirmed(Long id);
    List<UserResponseDto> getUnconfirmedUsers();
}
