package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ItemScreenDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileProfileResponseDTO;
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

    public ViewMobileProfileResponseDTO build(AssociateEntity associate) {
        ViewMobileProfileResponseDTO screen = new ViewMobileProfileResponseDTO();

        screen.setType("PERFIL");
        screen.setTitle("Perfil do usuario");
        screen.setText("dados pessoal do usuario");

        ButtonScreenDTO buttonUpdate = new ButtonScreenDTO("Atualizar",
                String.format("%s/associates/%s", apiProperties.getBaseUrl(),associate.getId()));


        ButtonScreenDTO buttonCancel = new ButtonScreenDTO("Cancelar");

        screen.setButtonUpdate(buttonUpdate);
        screen.setButtonCancel(buttonCancel);

        ItemScreenDTO itemName = new ItemScreenDTO("idNameUser", FieldTypeEnum.INPUT_TEXT, "Nome do usuario", associate.getName());
        ItemScreenDTO itemDocument = new ItemScreenDTO("idDocumentUser", FieldTypeEnum.INPUT_TEXT, "Document do usuario", associate.getDocument());

        screen.setItems(List.of(itemName, itemDocument));

        return screen;
    }
}
