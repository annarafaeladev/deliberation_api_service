package br.com.deliberation_api.domain.repository;

import br.com.deliberation_api.domain.model.option.OptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OptionRepository extends MongoRepository<OptionEntity, String> {

}
