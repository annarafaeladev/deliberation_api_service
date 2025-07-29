package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.application.view.factory.ListOptionViewFactory;
import br.com.deliberation_api.application.view.factory.ListTopicViewFactory;
import br.com.deliberation_api.application.view.factory.OptionViewFactory;
import br.com.deliberation_api.application.view.factory.ProfileDetailsViewFactory;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.interfaces.service.AssociateService;
import br.com.deliberation_api.interfaces.service.TopicService;
import br.com.deliberation_api.interfaces.service.ViewTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MobileViewServiceImplTest {

    @Mock
    private TopicService topicService;

    @Mock
    private AssociateService associateService;

    @Mock
    private ViewTemplateService viewTemplateService;

    @Mock
    private ListTopicViewFactory listTopicView;

    @Mock
    private ListOptionViewFactory listOptionViewFactory;

    @Mock
    private OptionViewFactory optionViewFactory;

    @Mock
    private ProfileDetailsViewFactory profileDetailsViewFactory;

    @InjectMocks
    private MobileViewServiceImpl mobileViewService;

    private ViewTemplateResponseDTO dummyPage;
    private TopicEntity dummyTopic;
    private AssociateEntity dummyAssociate;
    private OptionResponseDTO dummyOption;

    @BeforeEach
    void setup() {
        dummyPage = new ViewTemplateResponseDTO();
        dummyTopic = new TopicEntity("topic", "");
        dummyTopic.setId("topic1");
        dummyAssociate = new AssociateEntity();
        dummyOption = new OptionResponseDTO(
                "topic1",
                "Title of Topic",
                "Description of Topic",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                "option1",
                "Option Title",
                "Option Description",
                0,
                0,
                "DRAW"
        );
    }

    @Test
    void getPage_ShouldReturnPage() {
        when(viewTemplateService.findById("page1")).thenReturn(dummyPage);

        ViewTemplateResponseDTO result = mobileViewService.getPage("page1");

        assertNotNull(result);
        verify(viewTemplateService).findById("page1");
    }

    @Test
    void getTopics_ShouldReturnBuiltView() {
        List<TopicEntity> topics = List.of(dummyTopic);

        when(viewTemplateService.findById("page1")).thenReturn(dummyPage);
        when(topicService.list()).thenReturn(topics);
        when(listTopicView.build(dummyPage, topics)).thenReturn(dummyPage);

        ViewTemplateResponseDTO result = mobileViewService.getTopics("page1");

        assertNotNull(result);
        verify(viewTemplateService).findById("page1");
        verify(topicService).list();
        verify(listTopicView).build(dummyPage, topics);
    }

    @Test
    void getPageOptions_ShouldReturnBuiltView() {
        when(viewTemplateService.findById("page1")).thenReturn(dummyPage);
        when(topicService.getById("topic1")).thenReturn(dummyTopic);
        when(listOptionViewFactory.build(dummyPage, dummyTopic)).thenReturn(dummyPage);

        ViewTemplateResponseDTO result = mobileViewService.getPageOptions("page1", "topic1");

        assertNotNull(result);
        verify(viewTemplateService).findById("page1");
        verify(topicService).getById("topic1");
        verify(listOptionViewFactory).build(dummyPage, dummyTopic);
    }

    @Test
    void getPageOptionByOptionId_ShouldReturnBuiltView() {
        when(viewTemplateService.findById("page1")).thenReturn(dummyPage);
        when(topicService.getOption("topic1", "option1")).thenReturn(dummyOption);
        when(optionViewFactory.build(dummyPage, dummyOption)).thenReturn(dummyPage);

        ViewTemplateResponseDTO result = mobileViewService.getPageOptionByOptionId("page1", "topic1", "option1");

        assertNotNull(result);
        verify(viewTemplateService).findById("page1");
        verify(topicService).getOption("topic1", "option1");
        verify(optionViewFactory).build(dummyPage, dummyOption);
    }

    @Test
    void getProfilePage_ShouldReturnBuiltView() {
        when(viewTemplateService.findById("page1")).thenReturn(dummyPage);
        when(associateService.getById("associate1")).thenReturn(dummyAssociate);
        when(profileDetailsViewFactory.build(dummyPage, dummyAssociate)).thenReturn(dummyPage);

        ViewTemplateResponseDTO result = mobileViewService.getProfilePage("page1", "associate1");

        assertNotNull(result);
        verify(viewTemplateService).findById("page1");
        verify(associateService).getById("associate1");
        verify(profileDetailsViewFactory).build(dummyPage, dummyAssociate);
    }
}
