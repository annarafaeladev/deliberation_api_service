package br.com.deliberation_api.application.view.factory;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
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

    public ViewTemplateResponseDTO build(ViewTemplateResponseDTO page, OptionResponseDTO option) {
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


        page.setItems(List.of(buttonYes, buttonNo));

        return page;
    }
}
