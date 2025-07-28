package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListTopicViewFactoryTest {

    private ListTopicViewFactory factory;

    @BeforeEach
    void setUp() {
        ApiProperties apiProperties = new ApiProperties();
        apiProperties.setBaseUrl("http://localhost"); // mock base URL
        factory = new ListTopicViewFactory(apiProperties);
    }

    @Test
    void build_ShouldSetItemsCorrectly() {
        Session session = new Session(TimeTypeEnum.DAY, 1);
        TopicEntity topic = new TopicEntity("Test Topic", "Test description");
        topic.setId("topic123");
        topic.setSession(session);

        ViewTemplateResponseDTO page = new ViewTemplateResponseDTO();

        page.setId("pageId");

        ViewTemplateResponseDTO result = factory.build(page, List.of(topic));

        List<AbstractItemScreenDTO> items = result.getItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertTrue(items.get(0) instanceof ButtonScreenDTO);

        ButtonScreenDTO button = (ButtonScreenDTO) items.get(0);
        assertEquals("Test Topic", button.getText());
        assertTrue(button.getUrl().contains("http://localhost/mobile/pages/{PAGE_ID}/topics/pageId"));
        assertTrue(button.isAvailable());
    }
}
