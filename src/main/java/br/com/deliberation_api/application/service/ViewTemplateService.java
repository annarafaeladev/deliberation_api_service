package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.view.dto.structure.ViewTemplateRequestDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.model.view.ViewTemplateEntity;
import br.com.deliberation_api.domain.repository.ViewTemplateRepository;
import br.com.deliberation_api.shared.exception.ViewNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewTemplateService {

    private final ViewTemplateRepository repository;

    public ViewTemplateService(ViewTemplateRepository repository) {
        this.repository = repository;
    }

    public ViewTemplateResponseDTO create(ViewTemplateRequestDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new ViewNotFoundException("View Template already exists");
        }

        ViewTemplateEntity entity = new ViewTemplateEntity();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        entity.setButtonOk(dto.getButtonOk());
        entity.setButtonCancel(dto.getButtonCancel());
        entity.setVisible(dto.isVisible());
        entity.setElements(dto.getElements());

        repository.save(entity);

        return buildResponse(entity);
    }

    public List<ViewTemplateResponseDTO> findAll() {
        List<ViewTemplateEntity> templates = repository.findAll();
        return templates.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    public ViewTemplateResponseDTO findById(String id) {
        ViewTemplateEntity entity = findOrThrow(id);
        return buildResponse(entity);
    }

    public ViewTemplateResponseDTO update(String id, ViewTemplateRequestDTO dto) {
        ViewTemplateEntity entity = findOrThrow(id);

        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        entity.setButtonOk(dto.getButtonOk());
        entity.setButtonCancel(dto.getButtonCancel());
        entity.setVisible(dto.isVisible());
        entity.setElements(dto.getElements());

        repository.save(entity);

        return buildResponse(entity);
    }

    public void delete(String id) {
        ViewTemplateEntity entity = findOrThrow(id);
        repository.delete(entity);
    }

    private ViewTemplateResponseDTO buildResponse(ViewTemplateEntity entity) {
        ViewTemplateResponseDTO dto = new ViewTemplateResponseDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setTitle(entity.getTitle());
        dto.setText(entity.getText());
        dto.setButtonOk(entity.getButtonOk());
        dto.setButtonCancel(entity.getButtonCancel());
        dto.setVisible(entity.isVisible());

        dto.setElements(entity.getElements());

        return dto;
    }

    private ViewTemplateEntity findOrThrow(String viewId) {
        return repository.findById(viewId)
                .orElseThrow(() -> new ViewNotFoundException("View template not found"));
    }

}

