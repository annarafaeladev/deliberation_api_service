package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.model.view.ViewTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ViewTemplateRepository extends MongoRepository<ViewTemplateEntity, String> {
    boolean existsByName(String name);
}
