package br.com.deliberation_api.application.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionDTO {
    @NotBlank(message = "option: title is require")
    private String  title;
    private String  description;
}
