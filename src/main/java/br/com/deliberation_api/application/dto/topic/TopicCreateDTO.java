package br.com.deliberation_api.application.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicCreateDTO {

    @NotBlank
    private String titulo;

    private String descricao;
}

