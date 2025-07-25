package br.com.deliberation_api.domain.model;

import br.com.deliberation_api.domain.enums.VoteAuditActionEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vote_audit")
@Getter
@Setter
public class VoteAuditEntity {

    @Id
    private String id;

    private String topicId;
    private String associateId;
    private VoteEnum vote;

    private VoteAuditActionEnum action;
    private LocalDateTime actionTimestamp;

    public VoteAuditEntity(VoteEntity voteEntity, VoteAuditActionEnum action) {
        this.topicId = voteEntity.getTopicId();
        this.associateId = voteEntity.getAssociateId();
        this.vote = voteEntity.getVote();
        this.action = action;
        this.actionTimestamp = LocalDateTime.now();
    }
}
