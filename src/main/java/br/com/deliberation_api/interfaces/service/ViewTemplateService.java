package br.com.deliberation_api.interfaces.service;


import br.com.deliberation_api.application.view.dto.structure.ViewTemplateRequestDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;

import java.util.List;

public interface ViewTemplateService {

    ViewTemplateResponseDTO create(ViewTemplateRequestDTO dto);

    List<ViewTemplateResponseDTO> findAll();

    ViewTemplateResponseDTO findById(String id);

    ViewTemplateResponseDTO update(String id, ViewTemplateRequestDTO dto);

    void delete(String id);
}
