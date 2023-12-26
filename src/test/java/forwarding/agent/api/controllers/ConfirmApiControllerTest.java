package forwarding.agent.api.controllers;

import forwarding.agent.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static forwarding.agent.util.ConfirmTestData.*;
import static forwarding.agent.util.UserTestData.USER_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConfirmApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConfirmApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturn200WhenConfirmUserWithValidId() throws Exception {
        when(userService.isUserExistsById(UNCONFIRMED_ID)).thenReturn(true);
        when(userService.isUserConfirmed(UNCONFIRMED_ID)).thenReturn(false);

        mockMvc.perform(
                put(CONFIRM_URL_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    @Test
    void shouldReturn404WhenUserDoesNotExist() throws Exception {
        when(userService.isUserExistsById(UNCONFIRMED_ID)).thenReturn(false);
        when(userService.isUserConfirmed(UNCONFIRMED_ID)).thenReturn(false);

        mockMvc.perform(
                put(CONFIRM_URL_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUserAlreadyConfirmed() throws Exception {
        when(userService.isUserExistsById(UNCONFIRMED_ID)).thenReturn(true);
        when(userService.isUserConfirmed(UNCONFIRMED_ID)).thenReturn(true);

        mockMvc.perform(
                put(CONFIRM_URL_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenInvalidId() throws Exception {
        mockMvc.perform(
                put(INVALID_CONFIRM_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200sAndJson() throws Exception {
        mockMvc.perform(get(CONFIRM_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
