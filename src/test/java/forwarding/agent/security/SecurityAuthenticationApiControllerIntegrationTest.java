package forwarding.agent.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import forwarding.agent.PostgreSQLTestContainerExtension;
import forwarding.agent.service.dto.AuthenticationRequestDto;
import forwarding.agent.util.AuthenticationTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;


import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class SecurityAuthenticationApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndJsonContentTypeWhenAuthenticateWithValidCredentials() throws Exception {
        AuthenticationRequestDto request = AuthenticationTestData.createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndTokenWhenAuthenticateWithValidCredentials() throws Exception {
        AuthenticationRequestDto request = AuthenticationTestData.createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertNotNull(actualResponse.get(AuthenticationTestData.AUTHENTICATION_RESPONSE_TOKEN_KEY));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKeys(AuthenticationTestData.AUTHENTICATION_RESPONSE_EMAIL_KEY,
                AuthenticationTestData.AUTHENTICATION_RESPONSE_TOKEN_KEY);
        assertEquals(request.email(), actualResponse.get(AuthenticationTestData.AUTHENTICATION_RESPONSE_EMAIL_KEY));
    }

    @Test
    void shouldReturn400AndJsonContentTypeWhenAuthenticateWithInvalidCredentials() throws Exception {
        AuthenticationRequestDto request = AuthenticationTestData.createInvalidCredentials();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn400AndExpectedMessageWhenAuthenticateWithInvalidCredentials() throws Exception {
        AuthenticationRequestDto request = AuthenticationTestData.createInvalidCredentials();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        String expectedMessage = AuthenticationTestData.AUTHENTICATION_WITH_INVALID_CREDENTIALS_MESSAGE;

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKey(AuthenticationTestData.RESPONSE_MESSAGE_KEY);
        assertEquals(expectedMessage, actualResponse.get(AuthenticationTestData.RESPONSE_MESSAGE_KEY));
    }
}
