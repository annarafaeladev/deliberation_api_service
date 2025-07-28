package br.com.deliberation_api.application.view.dto.component;

import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemScreenDTO extends AbstractItemScreenDTO {
    private String title;
    private String value;
    private String text;

    public ItemScreenDTO() {}

    public ItemScreenDTO(FieldTypeEnum type, String title) {
        this.setType(type);
        this.title = title;
    }

    public ItemScreenDTO(FieldTypeEnum type, String title, String value) {
        this.setType(type);
        this.title = title;
        this.value = value;
    }

    public ItemScreenDTO(String id, FieldTypeEnum type, String title, String value) {
        this.setId(id);
        this.setType(type);
        this.title = title;
        this.value = value;
    }
}
