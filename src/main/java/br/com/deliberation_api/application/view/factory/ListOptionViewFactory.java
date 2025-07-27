package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicOptionsResponseDTO;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ListOptionViewFactory {

    private final ApiProperties apiProperties;

    public ListOptionViewFactory(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public ViewMobileTopicOptionsResponseDTO build(TopicEntity topic) {
        ViewMobileTopicOptionsResponseDTO screen = new ViewMobileTopicOptionsResponseDTO();

        List<ButtonScreenDTO> items = topic.getOptions().stream()
                .map((option) -> new ButtonScreenDTO(option.getTitle(), String.format("%s/view/topics/%s/options/%s", apiProperties.getBaseUrl(), topic.getId(), option.getId())))
                .toList();

        screen.setType("SELECÃO");
        screen.setTitle("Lista de seleção");
        screen.setItems(items);

        return screen;
    }
}
