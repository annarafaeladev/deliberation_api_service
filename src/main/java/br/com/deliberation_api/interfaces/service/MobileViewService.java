package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;

public interface MobileViewService {

    ViewTemplateResponseDTO getPage(String pageId);

    ViewTemplateResponseDTO getTopics(String pageId);

    ViewTemplateResponseDTO getPageOptions(String pageId, String topicId);

    ViewTemplateResponseDTO getPageOptionByOptionId(String id, String topicId, String optionId);

    ViewTemplateResponseDTO getProfilePage(String pageId, String associateId);
}
