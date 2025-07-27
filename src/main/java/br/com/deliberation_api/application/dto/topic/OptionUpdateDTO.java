package br.com.deliberation_api.application.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionUpdateDTO {
    @NotBlank(message = "option: id is require")
    private String id;

    @NotBlank(message = "option: title is require")
    private String  title;

    private String  description;
}
