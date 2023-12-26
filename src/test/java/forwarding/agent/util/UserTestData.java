package forwarding.agent.util;

import forwarding.agent.persistense.entity.User;
import forwarding.agent.service.dto.UserResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

import static forwarding.agent.util.RoleTestData.craeteUncomfiredRole;
import static forwarding.agent.util.RoleTestData.createRole;

@UtilityClass
public class UserTestData {
    public static final Long USER_ID = 1L;
    public static final String EMAIL = "user@gmail.com";
    public static final String FIRST_NAME = "Igor";
    public static final String LAST_NAME = "Yakubovich";
    public static final String FATHER_NAME = "Sergeevich";
    public static final String PASSWORD = "fgdkjfg242";


    public static User createUserWithoutId() {
        return User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();
    }

    public static User createUserWithId() {
        return User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .roles(Set.of(createRole()))
                .build();
    }

    public static User craeteUnconfirmedUser() {
        return User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .roles(Set.of(craeteUncomfiredRole()))
                .build();
    }

    public static UserResponseDto createUserResponseDto() {
        return UserResponseDto.builder()
                .id(USER_ID)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .fatherName(FATHER_NAME)
                .build();
    }

    public static List<User> createListOfUnconfirmedUsers() {
        return List.of(
                craeteUnconfirmedUser(),
                User.builder()
                        .id(2L)
                        .firstName("Sergey")
                        .lastName("Petrov")
                        .fatherName("Maxim")
                        .password(PASSWORD)
                        .email("sergey@gmail.com")
                        .roles(Set.of(craeteUncomfiredRole()))
                        .build()
        );
    }

    public static List<UserResponseDto> createListOfUnconfirmedUsersDto() {
        return List.of(
                createUserResponseDto(),
                UserResponseDto.builder()
                        .id(2L)
                        .firstName("Sergey")
                        .lastName("Petrov")
                        .fatherName("Maxim")
                        .build()
        );
    }
}
