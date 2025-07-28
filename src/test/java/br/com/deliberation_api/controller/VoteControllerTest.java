package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.vote.VoteRequestDTO;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.interfaces.service.TopicService;
import br.com.deliberation_api.interfaces.service.VoteService;
import br.com.deliberation_api.shared.exception.VoteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @MockBean
    private TopicService topicService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldVoteSuccessfully() throws Exception {
        VoteRequestDTO request = new VoteRequestDTO("topicId", "optionId", "associateId", VoteEnum.YES);
        when(topicService.isValidTopicSession("topicId")).thenReturn(true);

        mockMvc.perform(post("/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(voteService).vote("topicId", "associateId", "optionId", VoteEnum.YES);
    }

    @Test
    void shouldThrowExceptionWhenSessionExpired() throws Exception {
        VoteRequestDTO request = new VoteRequestDTO("topicId", "optionId", "associateId", VoteEnum.YES);
        when(topicService.isValidTopicSession("topicId")).thenReturn(false);

        mockMvc.perform(post("/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assertThat(resolvedException).isInstanceOf(VoteException.class);
                    assertThat(resolvedException.getMessage()).isEqualTo("Voting session has expired");
                });

        verify(voteService, never()).vote(any(), any(), any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidPayload() throws Exception {
        String invalidPayload = "{}";

        mockMvc.perform(post("/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest());
    }
}
