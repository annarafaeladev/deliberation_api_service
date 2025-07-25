package br.com.deliberation_api.application.service.dto;


import br.com.deliberation_api.domain.enums.VoteEnum;

public record VoteRequestDTO(String associateId, VoteEnum vote) {}
