package br.com.deliberation_api.application.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PautaCreateDTO {

    @NotBlank
    private String titulo;

    private String descricao;
}

