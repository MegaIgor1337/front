package forwarding.agent.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import forwarding.agent.PostgreSQLTestContainerExtension;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static forwarding.agent.util.AuthenticationTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:testdata/clear_users_with_different_roles.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndJsonContentTypeWhenRegistrationWithValidParams() throws Exception {
        RegistrationRequestDto request = createRegistrationRequestDtoWithValidParams();
        HttpEntity<RegistrationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                REGISTRATION_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndTokenWhenRegistrationWithValidParams() throws Exception {
        RegistrationRequestDto request = createRegistrationRequestDtoWithValidParams();
        HttpEntity<RegistrationRequestDto> requestEntity = new HttpEntity<>(request);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                REGISTRATION_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        System.out.println(actualResponse);
        assertNotNull(actualResponse.get(AUTHENTICATION_RESPONSE_TOKEN_KEY));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKeys(AUTHENTICATION_RESPONSE_EMAIL_KEY,
                AUTHENTICATION_RESPONSE_TOKEN_KEY);
        assertEquals(request.email(), actualResponse.get(AUTHENTICATION_RESPONSE_EMAIL_KEY));
    }

    @Test
    void shouldReturn400AndJsonContentTypeWhenRegistrationWithInvalidCredentials() {
        AuthenticationRequestDto request = createInvalidCredentials();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }
}
