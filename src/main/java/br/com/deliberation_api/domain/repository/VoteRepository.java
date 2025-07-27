package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<VoteEntity, String> {
    List<VoteEntity> findByTopicId(String topicId);

    long countByTopicIdAndVote(String topicId, VoteEnum vote);

    void deleteByTopicId(String topicId);

    Optional<VoteEntity> findByTopicIdAndAssociateId(String topicId, String associateId);

}
