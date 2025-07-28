package br.com.deliberation_api.application.view.dto.structure;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateResponseDTO {
    private String id;
    private String type;
    private String name;
    private String title;
    private String text;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AbstractItemScreenDTO> elements;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AbstractItemScreenDTO> items;
    private ButtonScreenDTO buttonOk;
    private ButtonScreenDTO buttonCancel;
    private boolean visible;
}
