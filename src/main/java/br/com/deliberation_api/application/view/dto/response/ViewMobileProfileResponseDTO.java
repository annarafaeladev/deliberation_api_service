package br.com.deliberation_api.application.view.dto.response;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ItemScreenDTO;
import lombok.Data;

import java.util.List;

@Data
public class ViewMobileProfileResponseDTO {
    private String type;
    private String title;
    private String text;
    private List<ItemScreenDTO> items;
    private ButtonScreenDTO buttonUpdate;
    private ButtonScreenDTO buttonCancel;
}
