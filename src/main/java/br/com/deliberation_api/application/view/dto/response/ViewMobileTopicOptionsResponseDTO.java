package br.com.deliberation_api.application.view.dto.response;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import lombok.Data;

import java.util.List;

@Data
public class ViewMobileTopicOptionsResponseDTO {
    private String type;
    private String title;
    private String text;
    private List<ButtonScreenDTO> items;
}
