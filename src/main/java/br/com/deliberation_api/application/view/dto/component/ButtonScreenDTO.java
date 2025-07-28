package br.com.deliberation_api.application.view.dto.component;

import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ButtonScreenDTO extends AbstractItemScreenDTO {

    @NotBlank(message = "field text is required")
    private String text;
    private String url;
    private boolean isAvailable;
    private Map<String, Object> body = new HashMap<>();

    public ButtonScreenDTO() {
        this.setType(FieldTypeEnum.BUTTON);
    }

    public ButtonScreenDTO(String text) {
        this.setType(FieldTypeEnum.BUTTON);
        this.text =text;
    }

    public ButtonScreenDTO(String text, String url) {
        this.setType(FieldTypeEnum.BUTTON);
        this.text = text;
        this.url = url;
    }

    public ButtonScreenDTO(String text, String url, Map<String, Object> body) {
        this.setType(FieldTypeEnum.BUTTON);
        this.text = text;
        this.url = url;
        this.body = body;
    }
}
