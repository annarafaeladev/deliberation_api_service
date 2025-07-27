package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;

import java.util.List;

public interface AssociateService {

    AssociateEntity create(AssociateCreateDTO dto);

    List<AssociateEntity> list();

    AssociateEntity getById(String id);

    AssociateEntity update(String id, AssociateUpdateDTO request);

    void delete(String id);
}

