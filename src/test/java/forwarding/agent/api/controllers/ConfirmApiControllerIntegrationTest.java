package forwarding.agent.api.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import forwarding.agent.PostgreSQLTestContainerExtension;
import forwarding.agent.service.dto.UserResponseDto;
import forwarding.agent.util.AuthenticationTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Map;

import static forwarding.agent.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static forwarding.agent.util.ConfirmTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
public class ConfirmApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOkAndExpectedUserDtoResponseJson() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(
                CONFIRM_URL_BY_ID,
                HttpMethod.PUT,
                getAuthHttpEntity(),
                String.class
        );

        UserResponseDto actualResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UNCONFIRMED_USER_EMAIL, actualResponse.email());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        ResponseEntity<String> response = restTemplate.exchange(
                CONFIRM_URL_BY_Id_WITH_INVALID_USER_ID,
                HttpMethod.PUT,
                getAuthHttpEntity(),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void shouldReturnBadRequestWhenUserAlreadyConfirmed() {
        ResponseEntity<String> response = restTemplate.exchange(
                CONFIRM_URL_BY_Id_WITH_CONFIRMED_USER_ID,
                HttpMethod.PUT,
                getAuthHttpEntity(),
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @SneakyThrows
    private HttpEntity<String> getAuthHttpEntity() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, String> responseMap  = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
