package br.com.deliberation_api.application.view.dto.structure;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ViewTemplateRequestDTO {

    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "name is required")
    private String name;

    private String title;

    private String text;

    @Valid
    private List<@Valid AbstractItemScreenDTO> elements;

    @Valid
    private ButtonScreenDTO buttonOk;

    @Valid
    private ButtonScreenDTO buttonCancel;

    private boolean visible;
}