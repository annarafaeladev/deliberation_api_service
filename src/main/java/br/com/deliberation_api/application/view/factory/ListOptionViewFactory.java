package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
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

    public ViewTemplateResponseDTO build(ViewTemplateResponseDTO page, TopicEntity topic) {
        List<AbstractItemScreenDTO> items = topic.getOptions().stream()
                .map(option -> new ButtonScreenDTO(
                        option.getTitle(),
                        String.format("%s/mobile/topics/%s/options/%s", apiProperties.getBaseUrl(), topic.getId(), option.getId())
                ))
                .map(item -> (AbstractItemScreenDTO) item)
                .toList();

        page.setItems(items);

        return page;
    }
}
