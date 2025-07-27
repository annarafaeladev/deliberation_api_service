package br.com.deliberation_api.application.view.dto.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ButtonScreenDTO {
    private String text;
    private String url;
    private boolean isAvailable;

    private Map<String, Object> body = new HashMap<>();

    public ButtonScreenDTO() {}

    public ButtonScreenDTO(String text) {
        this.text = text;
    }

    public ButtonScreenDTO(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public ButtonScreenDTO(String text, String url, Map<String, Object> body) {
        this.text = text;
        this.url = url;
        this.body = body;
    }
}