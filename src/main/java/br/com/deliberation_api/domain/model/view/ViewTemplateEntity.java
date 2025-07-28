package br.com.deliberation_api.domain.model.view;

import br.com.deliberation_api.application.view.dto.component.AbstractItemScreenDTO;
import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonInclude;


@Data
@Document(collection = "view")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViewTemplateEntity {

    @Id
    private String id;

    private String type;

    @Indexed(unique = true)
    private String name;

    private String title;

    private String text;

    private List<AbstractItemScreenDTO> elements;

    private ButtonScreenDTO buttonOk;

    private ButtonScreenDTO buttonCancel;

    private boolean visible;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public void setName(String name) {
        this.name = name;

        if (name != null && (id == null || id.isBlank())) {
            this.id = generateIdFromName(name);
        }
    }

    private String generateIdFromName(String name) {
        String cleaned = name
                .replaceAll("\\s+", "")
                .replaceAll("[^a-zA-Z0-9_-]", "")
                .toLowerCase();
        return "template_" + cleaned;
    }
}
