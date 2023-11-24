package forwarding.agent.security;


import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.security.jwt.JwtUser;
import forwarding.agent.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username:" + username + "not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadUserByUsername -  loaded username: {} successfully loaded", username);
        return jwtUser;
    }
}
