package br.com.deliberation_api.application.dto.vote;


import br.com.deliberation_api.domain.enums.VoteEnum;
import jakarta.validation.constraints.NotBlank;

public record VoteRequestDTO(@NotBlank(message = "topicId is required") String topicId, @NotBlank(message = "associateId is required") String associateId, VoteEnum vote) {}
