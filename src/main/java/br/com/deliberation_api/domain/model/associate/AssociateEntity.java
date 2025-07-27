package br.com.deliberation_api.domain.model.associate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "associate")
public class AssociateEntity {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String document;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public AssociateEntity() {}

    public AssociateEntity(String name, String document) {
        this.name = name;
        this.document = document;
    }
}

