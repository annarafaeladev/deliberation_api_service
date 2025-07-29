package br.com.deliberation_api.application.dto.topic;

import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponseDTO {

    private String id;
    private String title;
    private String description;
    private Session session;
    private List<OptionDTO> options;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TopicResponseDTO(TopicEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.session = entity.getSession();


        if (entity.getOptions() != null) {
            this.options = entity.getOptions().stream()
                    .map(option -> {
                        OptionDTO optionDTO = new OptionDTO();
                        optionDTO.setTitle(option.getTitle());
                        optionDTO.setDescription(option.getDescription());

                        return optionDTO;
                    })
                    .collect(Collectors.toList());
        }
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}

