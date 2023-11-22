package forwarding.agent.util;

import forwarding.agent.persistense.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTestData {
    public static final String EMAIL = "user@gmail.com";

    public static User createUserWithoutId() {
        return User.builder()
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .password("fgdkjfg242")
                .email(EMAIL)
                .build();
    }

    public static User createUserWithId() {
        return User.builder()
                .id(1L)
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .password("fgdkjfg242")
                .email(EMAIL)
                .build();
    }
}
