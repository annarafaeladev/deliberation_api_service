package br.com.deliberation_api.application.dto.topic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TopicCreateDTO {

    @NotBlank(message = "title is require")
    private String title;

    @NotBlank(message = "description is require")
    private String description;

    @NotEmpty(message = "options is required and must contain at least one option")
    @Valid
    private List<OptionDTO> options;
}
