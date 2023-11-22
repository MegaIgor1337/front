package forwarding.agent.service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidateConstants {
    public static final String REGEXP_VALIDATE_EMAIL = "^(?=.{6,}$)[\\s]*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-.\\/:;<=>?\\[\\]\\^_{}][a-zA-z0-9]+)*@([\\w]+(-[\\w]+)?)(\\.[\\w]+[-][\\w]+)*(\\.[a-z]{2,})+[\\s]*$";
    public static final String REGEXP_VALIDATE_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])\\w{4,}$";
}
