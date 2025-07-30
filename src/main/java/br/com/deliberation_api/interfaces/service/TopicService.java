package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.application.dto.topic.*;
import br.com.deliberation_api.domain.model.vote.VoteEntity;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;

import java.util.List;

public interface TopicService {
    TopicResponseDTO create(TopicCreateDTO dto);

    List<TopicEntity> list();

    List<TopicResponseDTO> listTopics();

    TopicResponseDTO getByTopicId(String topicId);

    TopicEntity getById(String topicId);

    boolean isValidTopicSession(String topicId);

    TopicResponseDTO update(String topicId, TopicUpdateDTO topicUpdateDto);

    void delete(String topicId);

    Session openSession(String topicId, SessionRequestDTO sessionRequestDTO);

    Session restartSession(String topicId, SessionRequestDTO sessionRequestDTO);

    Session closeSession(String topicId);

    OptionResponseDTO getOption(String topicId, String optionId);

    VoteEntity getVoteByTopicAndAssociate(String topicId, String associateId, String optionId);
}
