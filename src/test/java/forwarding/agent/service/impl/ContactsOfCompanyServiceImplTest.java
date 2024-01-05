package forwarding.agent.service.impl;

import forwarding.agent.api.exceptions.ContactsDoNotExistException;
import forwarding.agent.persistense.entity.ContactsOfCompany;
import forwarding.agent.persistense.repository.ContactsOfCompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static forwarding.agent.util.ContactsOfCompanyTestData.createContactsOfCompany;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactsOfCompanyServiceImplTest {
    @InjectMocks
    private ContactsOfCompanyServiceImpl contactsOfCompanyService;
    @Mock
    private ContactsOfCompanyRepository contactsOfCompanyRepository;

    @Test
    void shouldReturnListOfEmails() {
        List<ContactsOfCompany> contactsOfCompanies = createContactsOfCompany();

        when(contactsOfCompanyRepository.findAll()).thenReturn(contactsOfCompanies);

        List<String> result = contactsOfCompanyService.getListOfEmailForContact();

        assertEquals(contactsOfCompanies.size(), result.size());
        assertEquals(contactsOfCompanies.get(0).getEmail(), result.get(0));
        assertEquals(contactsOfCompanies.get(1).getEmail(), result.get(1));
        assertEquals(contactsOfCompanies.get(2).getEmail(), result.get(2));
    }

    @Test
    void shouldThrowExceptionContactsDoNotExist() {
        List<ContactsOfCompany> contactsOfCompanies = List.of();

        when(contactsOfCompanyRepository.findAll()).thenReturn(contactsOfCompanies);

        assertThrows(ContactsDoNotExistException.class, () ->
                contactsOfCompanyService.getListOfEmailForContact());
    }
}
