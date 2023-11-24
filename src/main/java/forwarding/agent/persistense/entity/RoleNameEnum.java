package forwarding.agent.persistense.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleNameEnum implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_SUPER_ADMIN,
    ROLE_ACCOUNTANT,
    ROLE_UNCONFIRMED_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
