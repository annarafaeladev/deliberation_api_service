package br.com.deliberation_api.application.view.dto.component;

import br.com.deliberation_api.shared.enums.FieldTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ButtonScreenDTO.class, name = "BUTTON"),
        @JsonSubTypes.Type(value = ItemScreenDTO.class, name = "INPUT_TEXT"),
        @JsonSubTypes.Type(value = ItemScreenDTO.class, name = "TEXT"),
        @JsonSubTypes.Type(value = ItemScreenDTO.class, name = "INPUT_NUMBER"),
        @JsonSubTypes.Type(value = ItemScreenDTO.class, name = "INPUT_DATE")
})
public class AbstractItemScreenDTO {
    private String id;
    @JsonIgnore
    private FieldTypeEnum type;
}