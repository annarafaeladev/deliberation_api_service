package br.com.deliberation_api.infrastructure.exception;

import br.com.deliberation_api.controller.AssociateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TestExceptionController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testVoteException() throws Exception {
        mockMvc.perform(post("/test/vote-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Vote Error"))
                .andExpect(jsonPath("$.message").value("Voting session has expired"));
    }

    @Test
    void testSessionException() throws Exception {
        mockMvc.perform(post("/test/session-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Session Error"));
    }

    @Test
    void testTopicNotFoundException() throws Exception {
        mockMvc.perform(post("/test/topic-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Topic Not Found"));
    }

    @Test
    void testGenericException() throws Exception {
        mockMvc.perform(post("/test/generic-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }

    @Test
    void testValidationException() throws Exception {
        mockMvc.perform(post("/test/validation-error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation error"));
    }
}
