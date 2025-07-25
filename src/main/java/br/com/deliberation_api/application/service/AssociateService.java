package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.service.dto.AssociateCreateDTO;
import br.com.deliberation_api.domain.model.AssociateEntity;
import br.com.deliberation_api.domain.repository.AssociateRepository;
import org.springframework.stereotype.Service;


@Service
public class AssociateService {

    private final AssociateRepository repository;

    public AssociateService(AssociateRepository repository) {
        this.repository = repository;
    }

    public AssociateEntity create(AssociateCreateDTO dto) {
        AssociateEntity associado = new AssociateEntity();
        associado.setNome(dto.getNome());
        associado.setCpf(dto.getCpf());
        return repository.save(associado);
    }
}

