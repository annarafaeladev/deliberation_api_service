package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateResponseDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;

import java.util.List;

public interface AssociateService {

    AssociateResponseDTO create(AssociateCreateDTO dto);

    List<AssociateResponseDTO> list();

    AssociateResponseDTO getTopicById(String id);
    AssociateEntity getById(String id);

    AssociateResponseDTO update(String id, AssociateUpdateDTO request);

    void delete(String id);
}

