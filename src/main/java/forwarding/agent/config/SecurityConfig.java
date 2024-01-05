package forwarding.agent.config;

import forwarding.agent.api.exceptions.RestAuthenticationEntryPoint;
import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.security.jwt.JwtTokenFilterConfigurer;
import forwarding.agent.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/registration",
            "/api/v1/getEmailsForContact",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };
    private static final String USER_URL = "/api/v1/users/**";
    private static final String UNCONFIRMED_USER_URL = "/api/v1/unconfirmed/**";
    private static final String ADMIN_URL = "/api/v1/admin/**";
    private static final String SUPER_ADMIN_URL = "/api/v1/superAdmin/**";
    private static final String ACCOUNTANT_URL = "/api/v1/accountant/**";

    private final JwtTokenProvider jwtTokenProvider;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(UNCONFIRMED_USER_URL).hasAnyAuthority(RoleNameEnum.ROLE_UNCONFIRMED_USER.getAuthority())
                .requestMatchers(USER_URL).hasAnyAuthority(RoleNameEnum.ROLE_USER.getAuthority())
                .requestMatchers(ADMIN_URL).hasAnyAuthority(RoleNameEnum.ROLE_ADMIN.getAuthority())
                .requestMatchers(SUPER_ADMIN_URL).hasAnyAuthority(RoleNameEnum.ROLE_SUPER_ADMIN.getAuthority())
                .requestMatchers(ACCOUNTANT_URL).hasAnyAuthority(RoleNameEnum.ROLE_ACCOUNTANT.getAuthority())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .httpBasic().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


