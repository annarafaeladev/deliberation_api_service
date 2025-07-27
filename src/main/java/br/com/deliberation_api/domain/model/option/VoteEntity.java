package br.com.deliberation_api.domain.model.option;

import br.com.deliberation_api.domain.enums.VoteEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "vote")
@CompoundIndex(name = "topic_associate_option_unique_idx", def = "{'topicId': 1, 'associateId': 1, 'optionId': 1}", unique = true)
public class VoteEntity {

    @Id
    private String id;

    private final String topicId;

    private final String associateId;

    private final String optionId;

    private final VoteEnum vote;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public VoteEntity(String topicId, String associateId, String optionId, VoteEnum vote) {
        this.topicId = topicId;
        this.associateId = associateId;
        this.optionId = optionId;
        this.vote = vote;
    }

}

