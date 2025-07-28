package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OptionViewFactoryTest {

    private OptionViewFactory factory;
    private ApiProperties apiProperties;

    @BeforeEach
    void setUp() {
        apiProperties = mock(ApiProperties.class);
        when(apiProperties.getBaseUrl()).thenReturn("https://example.com");
        factory = new OptionViewFactory(apiProperties);
    }

    @Test
    void build_ShouldReturnPageWithYesAndNoButtons() {
        // Arrange
        OptionResponseDTO option =  new OptionResponseDTO(
                "topic123",
                "Title of Topic",
                "Description of Topic",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                "option456",
                "Option Title",
                "Option Description",
                0,
                0,
                "DRAW");

        ViewTemplateResponseDTO page = new ViewTemplateResponseDTO();

        // Act
        ViewTemplateResponseDTO result = factory.build(page, option);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getItems());
        assertEquals(2, result.getItems().size());

        ButtonScreenDTO yesButton = (ButtonScreenDTO) result.getItems().get(0);
        ButtonScreenDTO noButton = (ButtonScreenDTO) result.getItems().get(1);

        assertEquals("Sim", yesButton.getText());
        assertEquals("Não", noButton.getText());

        assertEquals("https://example.com/votes", yesButton.getUrl());
        assertEquals("https://example.com/votes", noButton.getUrl());

        Map<String, Object> bodyYes = yesButton.getBody();
        Map<String, Object> bodyNo = noButton.getBody();

        assertEquals("topic123", bodyYes.get("topicId"));
        assertEquals("option456", bodyYes.get("optionId"));
        assertEquals("YES", bodyYes.get("vote"));

        assertEquals("topic123", bodyNo.get("topicId"));
        assertEquals("option456", bodyNo.get("optionId"));
        assertEquals("NO", bodyNo.get("vote"));
    }
}
