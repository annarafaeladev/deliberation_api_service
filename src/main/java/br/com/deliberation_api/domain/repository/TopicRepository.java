package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.model.topic.TopicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<TopicEntity, String> {
}
