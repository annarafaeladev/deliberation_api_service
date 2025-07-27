package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.application.dto.topic.OptionResponseDTO;
import br.com.deliberation_api.application.dto.topic.SessionRequestDTO;
import br.com.deliberation_api.application.dto.topic.TopicCreateDTO;
import br.com.deliberation_api.application.dto.topic.TopicUpdateDTO;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;

import java.util.List;

public interface TopicService {
    TopicEntity create(TopicCreateDTO dto);

    List<TopicEntity> list();

    TopicEntity getByTopicId(String topicId);

    boolean isValidTopicSession(String topicId);

    TopicEntity update(String topicId, TopicUpdateDTO topicUpdateDto);

    void delete(String topicId);

    TopicEntity openSession(String topicId, SessionRequestDTO sessionRequestDTO);

    TopicEntity restartSession(String topicId, SessionRequestDTO sessionRequestDTO);

    TopicEntity closeSession(String topicId);

    OptionResponseDTO getOption(String topicId, String optionId);

    VoteEntity getVoteByTopicAndAssociate(String topicId, String associateId, String optionId);
}
