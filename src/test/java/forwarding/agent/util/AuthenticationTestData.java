package forwarding.agent.util;

import forwarding.agent.persistense.entity.Role;
import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import lombok.experimental.UtilityClass;


import java.util.Set;


@UtilityClass
public class AuthenticationTestData {
    public static final String AUTHENTICATION_RESPONSE_TOKEN_KEY = "token";
    public static final String AUTHENTICATION_RESPONSE_EMAIL_KEY = "email";
    public static final String AUTHENTICATION_WITH_INVALID_CREDENTIALS_MESSAGE = "Wrong email or password";
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String AUTH_URL_TEMPLATE = "/api/v1/auth/login";
    public static final String REGISTRATION_URL_TEMPLATE = "/api/v1/auth/registration";
    private static final String NO_SUCH_EMAIL = "no-such-email@mail.com";
    private static final String EMAIL = "user@gmail.com";
    private static final String ADMIN_EMAIL = "user-admin@mail.com";
    private static final String PASSWORD = "password2rF";
    private static final String FIRST_NAME = "Igor";
    private static final String LAST_NAME = "Yakubovich";
    private static final String FATHER_NAME = "Sergeevich";
    public static final String ENCODED_PASSWORD = "$2a$12$nU3NTASAvpso5NuAr..2UOQL15kPiJ.IiwFDVZVMP86TUvZrSrf0m";
    private static final String USER_ROLE_PASSWORD = "user-role";


    public static AuthenticationRequestDto createInvalidCredentials() {
        return AuthenticationRequestDto.builder()
                .email(NO_SUCH_EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static AuthenticationRequestDto createCredentialsForUserWithRoleUserOnly() {
        return AuthenticationRequestDto.builder()
                .email(EMAIL)
                .password(USER_ROLE_PASSWORD)
                .build();
    }

    public static AuthenticationUserDto createAuthenticationUserDto() {
        return AuthenticationUserDto.builder()
                .email(EMAIL)
                .roleNames(Set.of(RoleNameEnum.ROLE_USER))
                .build();
    }

    public static User createUserForAuthentication() {
        return User.builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .roles(Set.of(
                        Role.builder().id(1L).roleName(RoleNameEnum.ROLE_USER).build())
                ).email(ADMIN_EMAIL)
                .password(ENCODED_PASSWORD)
                .build();
    }

    public static RegistrationRequestDto createRegistrationRequestDtoWithValidParams() {
        return RegistrationRequestDto.builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .password(PASSWORD)
                .build();
    }
}
