package br.com.deliberation_api.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "topic")
public class TopicEntity {

    @Id
    private String id;

    @NotBlank(message = "title is require")
    private String title;

    @NotBlank(message = "description is require")
    private String description;

    private SessionEntity session;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public TopicEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public boolean isAvailable() {
        return session != null && session.isAvailable();
    }
}

