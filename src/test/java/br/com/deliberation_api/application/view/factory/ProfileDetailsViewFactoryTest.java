package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ItemScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileDetailsViewFactoryTest {

    private ApiProperties apiProperties;
    private ProfileDetailsViewFactory factory;

    @BeforeEach
    void setUp() {
        apiProperties = mock(ApiProperties.class);
        factory = new ProfileDetailsViewFactory(apiProperties);
    }

    @Test
    void shouldBuildProfileDetailsCorrectly() {
                AssociateEntity associate = new AssociateEntity();
        associate.setId("123");
        associate.setName("Ana Maria");
        associate.setDocument("12345678900");

        ViewTemplateResponseDTO page = new ViewTemplateResponseDTO();

        when(apiProperties.getBaseUrl()).thenReturn("https://api.test.com");

        ViewTemplateResponseDTO result = factory.build(page, associate);

        assertNotNull(result);
        assertNotNull(result.getButtonOk());
        assertEquals("Atualizar", result.getButtonOk().getText());
        assertEquals("https://api.test.com/associates/123", result.getButtonOk().getUrl());

        List<?> items = result.getItems();
        assertEquals(2, items.size());

        ItemScreenDTO nameItem = (ItemScreenDTO) items.get(0);
        assertEquals("idNameUser", nameItem.getId());
        assertEquals(FieldTypeEnum.INPUT_TEXT, nameItem.getType());
        assertEquals("Nome do usuario", nameItem.getTitle());
        assertEquals("Ana Maria", nameItem.getValue());

        ItemScreenDTO docItem = (ItemScreenDTO) items.get(1);
        assertEquals("idDocumentUser", docItem.getId());
        assertEquals(FieldTypeEnum.INPUT_TEXT, docItem.getType());
        assertEquals("Document do usuario", docItem.getTitle());
        assertEquals("12345678900", docItem.getValue());
    }
}
