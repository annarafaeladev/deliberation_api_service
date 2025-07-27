package br.com.deliberation_api.application.dto.topic;

import java.time.LocalDateTime;

public record ResultResponseDTO(

    String topicId,
    String title,
    String description,
    LocalDateTime openAt,
    LocalDateTime closeAt,
    long totalYes,
    long totalNo,
    String result){}

