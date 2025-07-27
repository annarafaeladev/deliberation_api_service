package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.VoteAuditEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteAuditRepository extends MongoRepository<VoteAuditEntity, String> {
    List<VoteAuditEntity> findByTopicId(String topicId);
}
