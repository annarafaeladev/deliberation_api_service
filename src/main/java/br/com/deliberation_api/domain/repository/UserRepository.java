package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
