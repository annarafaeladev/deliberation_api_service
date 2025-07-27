package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ItemScreenDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicFormResponseDTO;
import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateTopicViewFactory {

    private final ApiProperties apiProperties;

    public CreateTopicViewFactory(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public ViewMobileTopicFormResponseDTO build() {
        ViewMobileTopicFormResponseDTO screen = new ViewMobileTopicFormResponseDTO();

        screen.setType("FORMULARIO");
        screen.setTitle("Cadastrar tópico");
        screen.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        screen.setItems(List.of(
                new ItemScreenDTO(FieldTypeEnum.INPUT_TEXT, "idFieldName", "Nome do topico"),
                new ItemScreenDTO(FieldTypeEnum.INPUT_TEXT, "idFieldDescription", "Descrição do topico")
        ));

        ButtonScreenDTO buttonOk = new ButtonScreenDTO();
        buttonOk.setText("Criar tópico");
        buttonOk.setUrl(String.format("%s/topics", apiProperties.getBaseUrl()));

        ButtonScreenDTO buttonCancel = new ButtonScreenDTO();
        buttonCancel.setText("Cancelar");

        screen.setButtonOk(buttonOk);
        screen.setButtonCancel(buttonCancel);

        return screen;
    }
}
