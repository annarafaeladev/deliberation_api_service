package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.model.option.OptionEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListOptionViewFactoryTest {

    private ListOptionViewFactory factory;
    private ApiProperties apiProperties;

    @BeforeEach
    void setUp() {
        apiProperties = mock(ApiProperties.class);
        when(apiProperties.getBaseUrl()).thenReturn("https://example.com");

        factory = new ListOptionViewFactory(apiProperties);
    }

    @Test
    void build_ShouldSetItemsCorrectly() {
        OptionEntity option = new OptionEntity("Title option", "description option");
        option.setId("optionId");

        TopicEntity topic = new TopicEntity("Title", "description");
        topic.setId("topicId");
        topic.setOptions(List.of(option));

        ViewTemplateResponseDTO page = new ViewTemplateResponseDTO();

        ViewTemplateResponseDTO result = factory.build(page, topic);

        List<AbstractItemScreenDTO> items = result.getItems();
        assertNotNull(items);
        assertEquals(1, items.size());

        assertTrue(items.get(0) instanceof ButtonScreenDTO);

        ButtonScreenDTO button = (ButtonScreenDTO) items.get(0);

        assertEquals("Title option", button.getText());
        assertTrue(button.getUrl().contains("/mobile/topics/topicId/options/optionId"));
    }
}
