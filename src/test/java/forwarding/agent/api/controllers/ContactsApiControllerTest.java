package forwarding.agent.api.controllers;

import forwarding.agent.api.exceptions.ContactsDoNotExistException;
import forwarding.agent.service.ContactsOfCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static forwarding.agent.util.ContactsOfCompanyTestData.CONTACTS_OF_COMPANY_URL;
import static forwarding.agent.util.ContactsOfCompanyTestData.createEmailsContactsOfCompany;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContactsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactsOfCompanyService contactsOfCompanyService;

    @Test
    void shouldReturn200WhenContactsExist() throws Exception {
        when(contactsOfCompanyService.getListOfEmailForContact()).thenReturn(createEmailsContactsOfCompany());

        mockMvc.perform(
                get(CONTACTS_OF_COMPANY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenContactsDoNotExist() throws Exception {
        when(contactsOfCompanyService.getListOfEmailForContact())
                .thenThrow(ContactsDoNotExistException.class);

        mockMvc.perform(
                get(CONTACTS_OF_COMPANY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
