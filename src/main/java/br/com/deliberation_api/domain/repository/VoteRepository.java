package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<VoteEntity, String> {
    List<VoteEntity> findByTopicId(String topicId);

    long countByTopicIdAndVote(String topicId, VoteEnum vote);
}
