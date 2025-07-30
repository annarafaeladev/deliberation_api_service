package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.vote.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<VoteEntity, String> {
    List<VoteEntity> findByTopicId(String topicId);

    long countByTopicIdAndOptionIdAndVote(String topicId, String optionId, VoteEnum vote);

    void deleteByTopicId(String topicId);

    Optional<VoteEntity> findByTopicIdAndAssociateIdAndOptionId(String topicId, String associateId, String optionId);

}
