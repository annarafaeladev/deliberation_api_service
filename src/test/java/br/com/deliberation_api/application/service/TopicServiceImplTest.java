package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.dto.topic.*;
import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.model.option.OptionEntity;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.domain.repository.OptionRepository;
import br.com.deliberation_api.domain.repository.TopicRepository;
import br.com.deliberation_api.shared.exception.OptionNotFoundException;
import br.com.deliberation_api.shared.exception.SessionException;
import br.com.deliberation_api.shared.exception.TopicNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private VoteServiceImpl voteService;

    @Mock
    private AssociateServiceImpl associateService;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    private TopicEntity topicEntity;
    private OptionEntity optionEntity;
    private SessionRequestDTO sessionRequestDTO;

    @BeforeEach
    void setup() {
        topicEntity = new TopicEntity("Title 1", "Description 1");
        topicEntity.setId("topic1");

        optionEntity = new OptionEntity("Option 1", "Option Desc");
        optionEntity.setId("option1");

        sessionRequestDTO = new SessionRequestDTO(null, null); // usa defaults
    }

    @Test
    void create_ShouldSaveTopicWithOptions() {
        TopicCreateDTO dto = mock(TopicCreateDTO.class);
        when(dto.getTitle()).thenReturn("Title 1");
        when(dto.getDescription()).thenReturn("Description 1");

        OptionDTO opt1 = new OptionDTO();
        opt1.setTitle("Option Title 1");
        opt1.setDescription("Option Description 1");

        OptionDTO opt2 = new OptionDTO();
        opt2.setTitle("Option Title 2");
        opt2.setDescription("Option Description 2");
        when(dto.getOptions()).thenReturn(List.of(opt1, opt2));

        when(optionRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        when(topicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TopicResponseDTO saved = topicService.create(dto);

        assertEquals("Title 1", saved.getTitle());
        assertEquals(2, saved.getOptions().size());
        verify(optionRepository).saveAll(anyList());
        verify(topicRepository).save(any());
    }

    @Test
    void list_ShouldReturnAllTopics() {
        when(topicRepository.findAll()).thenReturn(List.of(topicEntity));

        List<TopicEntity> topics = topicService.list();

        assertFalse(topics.isEmpty());
        verify(topicRepository).findAll();
    }

    @Test
    void getByTopicId_ShouldReturnTopic_WhenExists() {
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));

        TopicResponseDTO found = topicService.getByTopicId("topic1");

        assertEquals("Title 1", found.getTitle());
        verify(topicRepository).findById("topic1");
    }

    @Test
    void getByTopicId_ShouldThrowException_WhenNotExists() {
        when(topicRepository.findById("topic1")).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class, () -> topicService.getByTopicId("topic1"));
    }

    @Test
    void update_ShouldUpdateFields() {
        TopicUpdateDTO updateDTO = mock(TopicUpdateDTO.class);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(updateDTO.title()).thenReturn("Updated Title");
        when(updateDTO.description()).thenReturn("Updated Description");
        when(updateDTO.options()).thenReturn(null);

        when(topicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TopicResponseDTO updated = topicService.update("topic1", updateDTO);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Description", updated.getDescription());
        verify(topicRepository).save(any());
    }

    @Test
    void update_ShouldThrowOptionNotFound_WhenOptionNotFound() {
        TopicUpdateDTO updateDTO = mock(TopicUpdateDTO.class);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));

        OptionUpdateDTO opt = new OptionUpdateDTO();
        opt.setId("opt1");
        opt.setTitle("Old title");
        opt.setDescription("Old description");

        when(updateDTO.title()).thenReturn(null);
        when(updateDTO.description()).thenReturn(null);
        when(updateDTO.options()).thenReturn(List.of(opt));

        when(optionRepository.findById("opt1")).thenReturn(Optional.empty());

        assertThrows(OptionNotFoundException.class, () -> topicService.update("topic1", updateDTO));
    }

    @Test
    void delete_ShouldDeleteTopicAndOptions() {
        topicEntity.setOptions(List.of(optionEntity));
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));

        topicService.delete("topic1");

        verify(optionRepository).deleteAll(topicEntity.getOptions());
        verify(topicRepository).delete(topicEntity);
    }

    @Test
    void openSession_ShouldCreateSession_WhenNoActiveSession() {
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        assertNull(topicEntity.getSession());

        when(topicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Session result = topicService.openSession("topic1", sessionRequestDTO);

        assertNotNull(result);
        verify(topicRepository).save(topicEntity);
    }

    @Test
    void openSession_ShouldThrowSessionException_WhenSessionActive() {
        topicEntity.setSession(new Session(TimeTypeEnum.MINUTE,  10));
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));

        SessionException ex = assertThrows(SessionException.class, () -> topicService.openSession("topic1", sessionRequestDTO));
        assertTrue(ex.getMessage().contains("Active session already exists"));
    }

    @Test
    void restartSession_ShouldRestart_WhenValid() {
        Session session = spy(new Session(TimeTypeEnum.MINUTE, 10));
        topicEntity = spy(topicEntity);
        topicEntity.setSession(session);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(topicEntity.isAvailable()).thenReturn(true);

        when(topicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Session result = topicService.restartSession("topic1", sessionRequestDTO);

        verify(session).start(TimeTypeEnum.MINUTE, 1);
        verify(topicRepository).save(topicEntity);
        assertNotNull(result);
    }

    @Test
    void restartSession_ShouldThrowSessionException_WhenNoSession() {
        topicEntity.setSession(null);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));

        assertThrows(SessionException.class, () -> topicService.restartSession("topic1", sessionRequestDTO));
    }

    @Test
    void restartSession_ShouldThrowSessionException_WhenNotAvailable() {
        topicEntity = spy(topicEntity);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        assertThrows(SessionException.class, () -> topicService.restartSession("topic1", sessionRequestDTO));
    }

    @Test
    void closeSession_ShouldCloseSession_WhenAvailable() {
        Session session = spy(new Session(TimeTypeEnum.MINUTE, 10));
        topicEntity.setSession(session);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(topicEntity.isAvailable()).thenReturn(true);

        when(topicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Session result = topicService.closeSession("topic1");

        assertTrue(result.isClosedManually());
        verify(topicRepository).save(topicEntity);
    }

    @Test
    void closeSession_ShouldThrowSessionException_WhenNotAvailable() {
        Session session = new Session(TimeTypeEnum.MINUTE, 10);
        topicEntity = spy(topicEntity);
        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(topicEntity.isAvailable()).thenReturn(false);

        assertThrows(SessionException.class, () -> topicService.closeSession("topic1"));
    }

    @Test
    void getOption_ShouldReturnOptionResponseDTO() {
        topicEntity.setId("topic1");
        topicEntity.setTitle("Title");
        topicEntity.setDescription("Desc");
        Session session = new Session(TimeTypeEnum.MINUTE, 10);
        topicEntity.setSession(session);

        optionEntity.setId("option1");
        optionEntity.setTitle("Opt Title");
        optionEntity.setDescription("Opt Desc");

        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(optionRepository.findById("option1")).thenReturn(Optional.of(optionEntity));
        when(voteService.getCountByTopicIdAndVote("topic1", "option1", VoteEnum.YES)).thenReturn(5L);
        when(voteService.getCountByTopicIdAndVote("topic1", "option1", VoteEnum.NO)).thenReturn(3L);

        OptionResponseDTO dto = topicService.getOption("topic1", "option1");

        assertEquals("topic1", dto.topicId());
        assertEquals("Title", dto.topicTitle());
        assertEquals("Desc", dto.topicDescription());
        assertEquals("option1", dto.optionId());
        assertEquals("Opt Title", dto.optionTitle());
        assertEquals("Opt Desc", dto.optionDescription());
        assertEquals(5L, dto.totalYes());
        assertEquals(3L, dto.totalNo());
        assertEquals("APPROVED", dto.result());
    }

    @Test
    void getVoteByTopicAndAssociate_ShouldReturnVote() {
        AssociateEntity associate = new AssociateEntity();
        associate.setId("associate1");
        topicEntity.setId("topic1");

        when(topicRepository.findById("topic1")).thenReturn(Optional.of(topicEntity));
        when(optionRepository.findById("option1")).thenReturn(Optional.of(optionEntity));
        when(associateService.getById("associate1")).thenReturn(associate);
        when(voteService.getByTopicIdAndAssociateIdAndOptionId("topic1", "associate1", "option1"))
                .thenReturn(new VoteEntity("topic1", "associate1", "option1", VoteEnum.YES));

        VoteEntity vote = topicService.getVoteByTopicAndAssociate("topic1", "associate1", "option1");

        assertNotNull(vote);
        verify(voteService).getByTopicIdAndAssociateIdAndOptionId("topic1", "associate1", "option1");
    }
}
