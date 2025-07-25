package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.model.AssociateEntity;
import br.com.deliberation_api.domain.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociateRepository extends MongoRepository<AssociateEntity, String> {
}
