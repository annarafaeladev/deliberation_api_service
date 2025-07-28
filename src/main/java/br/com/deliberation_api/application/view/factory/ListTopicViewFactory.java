package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ListTopicViewFactory {

    private final ApiProperties apiProperties;

    public ListTopicViewFactory(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public ViewTemplateResponseDTO build(ViewTemplateResponseDTO page, List<TopicEntity> topics) {


        List<AbstractItemScreenDTO> items = topics.stream()
                .map(topic -> {
                    ButtonScreenDTO buttonScreenDTO = new ButtonScreenDTO(
                            topic.getTitle(),
                            String.format("%s/mobile/pages/{PAGE_ID}/topics/%s", apiProperties.getBaseUrl(), page.getId(), topic.getId())
                    );
                    buttonScreenDTO.setAvailable(topic.isAvailable());
                    return buttonScreenDTO;
                }).map(item -> (AbstractItemScreenDTO) item)
                .toList();


        page.setItems(items);

        return page;
    }
}
