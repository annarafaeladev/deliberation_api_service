package br.com.deliberation_api.application.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.deliberation_api.application.dto.associate.AssociateResponseDTO;
import br.com.deliberation_api.interfaces.service.AssociateService;
import br.com.deliberation_api.shared.exception.AssociateException;
import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.shared.exception.AssociateNotFoundException;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.repository.AssociateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository associateRepository;

    public AssociateServiceImpl(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public AssociateResponseDTO create(AssociateCreateDTO associateCreateDTO) {
        var document = cleanDocument(associateCreateDTO.document());
        validateDocument(document);

        AssociateEntity associate = new AssociateEntity(associateCreateDTO.name(), document);

        AssociateEntity save = associateRepository.save(associate);
        return new AssociateResponseDTO(save);
    }

    public List<AssociateResponseDTO> list() {
        List<AssociateEntity> all = associateRepository.findAll();

        return all.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AssociateResponseDTO getTopicById(String associateId) {
        AssociateEntity associateEntity = findTopicOrThrow(associateId);

        return convertToResponseDTO(associateEntity);
    }


    public AssociateEntity getById(String associateId) {
        return findTopicOrThrow(associateId);
    }

    public AssociateResponseDTO update(String associateId, AssociateUpdateDTO associateUpdateDTO) {
        AssociateEntity associate = findTopicOrThrow(associateId);

        if (associateUpdateDTO.document() == null && associateUpdateDTO.name() == null) {
            return convertToResponseDTO(associate);
        }

        if (associateUpdateDTO.document() != null) {
            var document = cleanDocument(associateUpdateDTO.document());
            validateDocument(document);
            associate.setDocument(document);
        }

        if (associateUpdateDTO.name() != null) {
            associate.setName(associateUpdateDTO.name());
        }

        associateRepository.save(associate);

        return convertToResponseDTO(associate);
    }

    public void delete(String topicId) {
        AssociateEntity topic = findTopicOrThrow(topicId);

        associateRepository.delete(topic);
    }

    private AssociateEntity findTopicOrThrow(String associateId) {
        return associateRepository.findById(associateId)
                .orElseThrow(() -> new AssociateNotFoundException("Associate not found with id " + associateId));
    }

    private void validateDocument(String document) {
        if (associateRepository.existsByDocument(document)) {
            throw new AssociateException("Document already registered");
        }

        var isValidDocument = new CPFValidator();

        if (!isValidDocument.invalidMessagesFor(document).isEmpty()) {
            throw new AssociateException("Associate document invalid");
        }
    }

    private AssociateResponseDTO convertToResponseDTO(AssociateEntity associate) {
        return new AssociateResponseDTO(associate);
    }

    private String cleanDocument(String document) {
        return  document.replaceAll("[.-]", "");
    }
}

