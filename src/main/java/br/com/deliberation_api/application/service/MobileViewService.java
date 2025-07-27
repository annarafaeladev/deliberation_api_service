package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.view.dto.response.ViewMobileProfileResponseDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicFormResponseDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicOptionsResponseDTO;
import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.view.factory.*;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.interfaces.service.AssociateService;
import br.com.deliberation_api.interfaces.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MobileViewService {

    private final TopicService topicService;
    private final AssociateService associateService;
    private final CreateTopicViewFactory createTopicViewFactory;
    private final ListTopicViewFactory listTopicView;
    private final ListOptionViewFactory listOptionViewFactory;
    private final OptionViewFactory optionViewFactory;
    private final ProfileDetailsViewFactory profileDetailsViewFactory;

    public MobileViewService(TopicService topicService, AssociateService associateService, CreateTopicViewFactory createTopicViewFactory, ListTopicViewFactory listTopicView, ListOptionViewFactory listOptionViewFactory, OptionViewFactory optionViewFactory, ProfileDetailsViewFactory profileDetailsViewFactory) {
        this.topicService = topicService;
        this.associateService = associateService;
        this.createTopicViewFactory = createTopicViewFactory;
        this.listTopicView = listTopicView;
        this.listOptionViewFactory = listOptionViewFactory;
        this.optionViewFactory = optionViewFactory;
        this.profileDetailsViewFactory = profileDetailsViewFactory;
    }

    public ViewMobileTopicFormResponseDTO getPageCreateTopic() {
        return createTopicViewFactory.build();
    }

    public ViewMobileTopicOptionsResponseDTO getTopics() {
        List<TopicEntity> topics = topicService.list();

        return listTopicView.build(topics);
    }

    public ViewMobileTopicOptionsResponseDTO getPageOptions(String topicId) {
        TopicEntity topic = topicService.getByTopicId(topicId);

        return listOptionViewFactory.build(topic);
    }

    public ViewMobileTopicOptionsResponseDTO getPageOptionByOptionId(String topicId, String optionId) {
        OptionResponseDTO option = topicService.getOption(topicId, optionId);

        return optionViewFactory.build(option);
    }

     public ViewMobileProfileResponseDTO getProfilePage(String associateId) {
        AssociateEntity associate = associateService.getById(associateId);

        return profileDetailsViewFactory.build(associate);
    }


}

