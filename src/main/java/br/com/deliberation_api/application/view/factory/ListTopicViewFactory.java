package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicOptionsResponseDTO;
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

    public ViewMobileTopicOptionsResponseDTO build(List<TopicEntity> topics) {

        ViewMobileTopicOptionsResponseDTO screen = new ViewMobileTopicOptionsResponseDTO();

        List<ButtonScreenDTO> items = topics.stream()
                .map(topic -> {
                    ButtonScreenDTO buttonScreenDTO = new ButtonScreenDTO(
                            topic.getTitle(),
                            String.format("%s/view/topics/%s/options", apiProperties.getBaseUrl(), topic.getId())
                    );
                    buttonScreenDTO.setAvailable(topic.isAvailable());
                    return buttonScreenDTO;
                })
                .toList();


        screen.setType("TOPICOS");
        screen.setTitle("Lista de topicos");
        screen.setText("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        screen.setItems(items);

        return screen;
    }
}
