package br.com.deliberation_api.application.dto.associate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AssociateCreateDTO(

    @NotBlank(message = "name is required")
    String name,

    @NotBlank(message = "document is required")
    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "document should be formatted as xxx.xxx.xxx-xx or xxxxxxxxxxx"
    )
    String document){}

