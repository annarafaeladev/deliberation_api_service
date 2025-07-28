package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ItemScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProfileDetailsViewFactory {

    private final ApiProperties apiProperties;

    public ProfileDetailsViewFactory(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public ViewTemplateResponseDTO build(ViewTemplateResponseDTO page, AssociateEntity associate) {

        ButtonScreenDTO buttonUpdate = new ButtonScreenDTO("Atualizar",
                String.format("%s/associates/%s", apiProperties.getBaseUrl(),associate.getId()));

        page.setButtonOk(buttonUpdate);

        ItemScreenDTO itemName = new ItemScreenDTO("idNameUser", FieldTypeEnum.INPUT_TEXT, "Nome do usuario", associate.getName());
        ItemScreenDTO itemDocument = new ItemScreenDTO("idDocumentUser", FieldTypeEnum.INPUT_TEXT, "Document do usuario", associate.getDocument());

        page.setItems(List.of(itemName, itemDocument));

        return page;
    }
}
