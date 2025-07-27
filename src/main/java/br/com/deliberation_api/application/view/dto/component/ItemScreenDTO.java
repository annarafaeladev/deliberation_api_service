package br.com.deliberation_api.application.view.dto.component;

import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemScreenDTO {
    private String id;
    private FieldTypeEnum type;
    private String title;
    private String value;

    public ItemScreenDTO(FieldTypeEnum type, String title) {
        this.type = type;
        this.title = title;
    }

    public ItemScreenDTO(FieldTypeEnum type, String title, String value) {
        this.type = type;
        this.title = title;
        this.value = value;
    }

    public ItemScreenDTO(String id, FieldTypeEnum type, String title, String value) {
        this.type = type;
        this.title = title;
        this.value = value;
        this.id = id;
    }
}