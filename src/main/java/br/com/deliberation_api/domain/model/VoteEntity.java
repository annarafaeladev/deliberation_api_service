package br.com.deliberation_api.domain.model;

import br.com.deliberation_api.domain.enums.VoteEnum;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "vote")
@CompoundIndex(name = "topic_associate_unique_idx", def = "{'topicId': 1, 'associateId': 1}", unique = true)
public class VoteEntity {

    @Id
    private String id;

    private final String topicId;

    private final String associateId;

    private final VoteEnum vote;

    public VoteEntity(String topicId, String associateId, VoteEnum vote) {
        this.topicId = topicId;
        this.associateId = associateId;
        this.vote = vote;
    }

}

