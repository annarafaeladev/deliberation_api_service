package br.com.deliberation_api.application.dto.topic;

import java.time.LocalDateTime;

public record OptionResponseDTO(

    String topicId,
    String topicTitle,
    String topicDescription,
    LocalDateTime openAt,
    LocalDateTime closeAt,
    String optionId,
    String optionTitle,
    String optionDescription,
    long totalYes,
    long totalNo,
    String result){}

