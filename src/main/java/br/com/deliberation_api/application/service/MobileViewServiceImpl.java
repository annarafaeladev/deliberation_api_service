package br.com.deliberation_api.application.service;


import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.dto.topic.TopicResponseDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.application.view.factory.*;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.interfaces.service.AssociateService;
import br.com.deliberation_api.interfaces.service.MobileViewService;
import br.com.deliberation_api.interfaces.service.TopicService;
import br.com.deliberation_api.interfaces.service.ViewTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
class MobileViewServiceImpl implements MobileViewService {

    private final TopicService topicService;
    private final AssociateService associateService;
    private final ViewTemplateService viewTemplateService;
    private final ListTopicViewFactory listTopicView;
    private final ListOptionViewFactory listOptionViewFactory;
    private final OptionViewFactory optionViewFactory;
    private final ProfileDetailsViewFactory profileDetailsViewFactory;

    public MobileViewServiceImpl(TopicService topicService, AssociateService associateService, ViewTemplateService viewTemplateService, ListTopicViewFactory listTopicView, ListOptionViewFactory listOptionViewFactory, OptionViewFactory optionViewFactory, ProfileDetailsViewFactory profileDetailsViewFactory) {
        this.topicService = topicService;
        this.associateService = associateService;
        this.viewTemplateService = viewTemplateService;
        this.listTopicView = listTopicView;
        this.listOptionViewFactory = listOptionViewFactory;
        this.optionViewFactory = optionViewFactory;
        this.profileDetailsViewFactory = profileDetailsViewFactory;
    }

    public ViewTemplateResponseDTO getPage(String pageId) {
        return viewTemplateService.findById(pageId);
    }

    public ViewTemplateResponseDTO getTopics(String pageId) {
        ViewTemplateResponseDTO page = viewTemplateService.findById(pageId);
        List<TopicEntity> topics = topicService.list();

        return listTopicView.build(page, topics);
    }

    public ViewTemplateResponseDTO getPageOptions(String pageId, String topicId) {
        ViewTemplateResponseDTO page = viewTemplateService.findById(pageId);
        TopicEntity topic = topicService.getById(topicId);

        return listOptionViewFactory.build(page, topic);
    }

    public ViewTemplateResponseDTO getPageOptionByOptionId(String id, String topicId, String optionId) {
        ViewTemplateResponseDTO page = viewTemplateService.findById(id);
        OptionResponseDTO option = topicService.getOption(topicId, optionId);

        return optionViewFactory.build(page, option);
    }

     public ViewTemplateResponseDTO getProfilePage(String pageId, String associateId) {
         ViewTemplateResponseDTO page = viewTemplateService.findById(pageId);
         AssociateEntity associate = associateService.getById(associateId);

        return profileDetailsViewFactory.build(page, associate);
    }


}

