package forwarding.agent.util;

import lombok.experimental.UtilityClass;

import static forwarding.agent.util.UserTestData.USER_ID;

@UtilityClass
public class ConfirmTestData {
    public static final String CONFIRM_URL = "/api/v1/admin/confirm/";
    public static final Long UNCONFIRMED_ID = 3L;
    public static final String INVALID_CONFIRM_URL = "/api/v1/admin/confirm";
    public static final String UNCONFIRMED_USER_EMAIL = "unconfirmed-user@mail.com";
    public static final String CONFIRM_URL_BY_ID = String.format("%s/%s", CONFIRM_URL, UNCONFIRMED_ID);
}
