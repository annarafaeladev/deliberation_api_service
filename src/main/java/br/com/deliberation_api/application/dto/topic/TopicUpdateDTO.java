package br.com.deliberation_api.application.dto.topic;


import jakarta.validation.Valid;

import java.util.List;

public record TopicUpdateDTO(String title, String description, @Valid List<OptionUpdateDTO> options) {}
