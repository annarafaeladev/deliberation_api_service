package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.topic.*;
import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.interfaces.service.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Autowired
    private ObjectMapper objectMapper;

    private TopicEntity topicEntity;
    private OptionResponseDTO optionResponseDTO;
    private VoteEntity voteEntity;

    @BeforeEach
    void setup() {
        topicEntity = new TopicEntity("Sample Title", "Sample Description");
        topicEntity.setId("topicId");
        topicEntity.setSession(new Session(TimeTypeEnum.DAY, 1));

        optionResponseDTO = new OptionResponseDTO(
                "topicId", "Sample Title", "Sample description",
                null, null,
                "optionId", "Option Title", "Option Desc",
                10, 5, "APPROVED"
        );

        voteEntity = new VoteEntity("topicId",  "associateId","optionId", VoteEnum.YES);

    }

    @Test
    void create_ShouldReturnCreatedTopic() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setTitle("Option title");
        optionDTO.setDescription("Description");
        TopicCreateDTO createDTO = new TopicCreateDTO("Sample Title", "Sample description", List.of(optionDTO));
        when(topicService.create(any(TopicCreateDTO.class))).thenReturn(new TopicResponseDTO(topicEntity));

        mockMvc.perform(post("/v1/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("topicId"))
                .andExpect(jsonPath("$.title").value("Sample Title"));

        verify(topicService).create(any(TopicCreateDTO.class));
    }

    @Test
    void list_ShouldReturnListOfTopics() throws Exception {
        when(topicService.listTopics()).thenReturn(List.of(new TopicResponseDTO(topicEntity)));

        mockMvc.perform(get("/v1/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("topicId"))
                .andExpect(jsonPath("$[0].title").value("Sample Title"));

        verify(topicService).listTopics();
    }

    @Test
    void getByTopicId_ShouldReturnTopic() throws Exception {
        when(topicService.getByTopicId("topicId")).thenReturn(new TopicResponseDTO(topicEntity));

        mockMvc.perform(get("/v1/topics/topicId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("topicId"))
                .andExpect(jsonPath("$.title").value("Sample Title"));

        verify(topicService).getByTopicId("topicId");
    }

    @Test
    void update_ShouldReturnUpdatedTopic() throws Exception {
        TopicUpdateDTO updateDTO = new TopicUpdateDTO(null, "Updated description", null);
        TopicEntity updatedEntity = new TopicEntity("Sample title", "Updated description");
        updatedEntity.setId("topicId");
        updatedEntity.setTitle("Sample Title");
        updatedEntity.setDescription("Updated description");

        when(topicService.update(eq("topicId"), any(TopicUpdateDTO.class))).thenReturn(new TopicResponseDTO(updatedEntity));

        mockMvc.perform(put("/v1/topics/topicId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(topicService).update(eq("topicId"), any(TopicUpdateDTO.class));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/v1/topics/topicId"))
                .andExpect(status().isNoContent());

        verify(topicService).delete("topicId");
    }

    @Test
    void openSession_ShouldReturnTopic() throws Exception {
        SessionRequestDTO dto = new SessionRequestDTO(null, null);
        when(topicService.openSession(eq("topicId"), any())).thenReturn(topicEntity.getSession());

        mockMvc.perform(post("/v1/topics/topicId/open-session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(topicService).openSession(eq("topicId"), any());
    }

    @Test
    void closeSession_ShouldReturnTopic() throws Exception {
        when(topicService.closeSession("topicId")).thenReturn(topicEntity.getSession());

        mockMvc.perform(patch("/v1/topics/topicId/close-session"))
                .andExpect(status().isOk());

        verify(topicService).closeSession("topicId");
    }

    @Test
    void restartSession_ShouldReturnTopic() throws Exception {
        SessionRequestDTO dto = new SessionRequestDTO(null, null);
        when(topicService.restartSession(eq("topicId"), any())).thenReturn(topicEntity.getSession());

        mockMvc.perform(patch("/v1/topics/topicId/restart-session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(topicService).restartSession(eq("topicId"), any());
    }

    @Test
    void getOption_ShouldReturnOptionResponse() throws Exception {
        when(topicService.getOption("topicId", "optionId")).thenReturn(optionResponseDTO);

        mockMvc.perform(get("/v1/topics/topicId/options/optionId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionId").value("optionId"))
                .andExpect(jsonPath("$.result").value("APPROVED"));

        verify(topicService).getOption("topicId", "optionId");
    }

    @Test
    void getVoteByTopicAndAssociate_ShouldReturnVoteEntity() throws Exception {
        when(topicService.getVoteByTopicAndAssociate("topicId", "associateId", "optionId"))
                .thenReturn(voteEntity);

        mockMvc.perform(get("/v1/topics/topicId/associates/associateId/vote/optionId"))
                .andExpect(status().isOk());

        verify(topicService).getVoteByTopicAndAssociate("topicId", "associateId", "optionId");
    }
}
