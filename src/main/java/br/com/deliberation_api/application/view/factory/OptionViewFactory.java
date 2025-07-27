package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicOptionsResponseDTO;
import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.infrastructure.config.ApiProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class OptionViewFactory {

    private final ApiProperties apiProperties;

    public OptionViewFactory(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public ViewMobileTopicOptionsResponseDTO build(OptionResponseDTO option) {
        ViewMobileTopicOptionsResponseDTO screen = new ViewMobileTopicOptionsResponseDTO();

        Map<String, Object> baseBody = new HashMap<>();
        baseBody.put("topicId", option.topicId());
        baseBody.put("optionId", option.optionId());


        Map<String, Object> bodyYes = new HashMap<>(baseBody);
        bodyYes.put("vote", "YES");

        Map<String, Object> bodyNo = new HashMap<>(baseBody);
        bodyNo.put("vote", "NO");
        ButtonScreenDTO buttonYes = new ButtonScreenDTO("Sim",
                String.format("%s/votes", apiProperties.getBaseUrl()), bodyYes);
        ButtonScreenDTO buttonNo = new ButtonScreenDTO("Não",
                String.format("%s/votes", apiProperties.getBaseUrl()), bodyNo);

        screen.setType("OPÇÃO");
        screen.setTitle(option.optionTitle());
        screen.setText(option.optionDescription());
        screen.setItems(List.of(buttonYes, buttonNo));

        return screen;
    }
}
