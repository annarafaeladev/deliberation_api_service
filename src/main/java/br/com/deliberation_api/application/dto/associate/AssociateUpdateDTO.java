package br.com.deliberation_api.application.dto.associate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AssociateUpdateDTO(
        @NotBlank String name,
        @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "Document should be formatted as xxx.xxx.xxx-xx or xxxxxxxxxxx")
        String document)
{}

