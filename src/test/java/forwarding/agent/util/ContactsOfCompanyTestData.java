package forwarding.agent.util;

import forwarding.agent.persistense.entity.ContactsOfCompany;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ContactsOfCompanyTestData {
    public static final String CONTACTS_OF_COMPANY_URL = "/api/v1/getEmailsForContact";

    public static List<ContactsOfCompany> createContactsOfCompany() {
        return List.of(
                ContactsOfCompany.builder()
                        .id(1L)
                        .email("admin1@mail.ru")
                        .build(),
                ContactsOfCompany.builder()
                        .id(2L)
                        .email("admin2@mail.ru")
                        .build(),
                ContactsOfCompany.builder()
                        .id(3L)
                        .email("admin3@mail.ru")
                        .build()
        );
    }

    public static List<String> createEmailsContactsOfCompany() {
        return List.of(
                "admin1@mail.ru",
                "admin2@mail.ru",
                "admin3@mail.ru"
        );
    }
}
