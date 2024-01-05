package forwarding.agent.api.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import forwarding.agent.PostgreSQLTestContainerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static forwarding.agent.util.ContactsOfCompanyTestData.CONTACTS_OF_COMPANY_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_contacts_of_company.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_contacts_of_company.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
public class ContactsApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOkAndExpectedUserDtoResponseJson() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(
                CONTACTS_OF_COMPANY_URL,
                HttpMethod.GET,
                null,
                String.class
        );

        List<String> actualResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, actualResponse.size());
    }

    @Test
    @Sql(scripts = "classpath:testdata/clear_contacts_of_company.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnNotFoundWhenContactsDoNotExist() {
        ResponseEntity<String> response = restTemplate.exchange(
                CONTACTS_OF_COMPANY_URL,
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
