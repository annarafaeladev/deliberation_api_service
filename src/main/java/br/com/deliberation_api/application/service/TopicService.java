package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.exception.SessionException;
import br.com.deliberation_api.application.exception.TopicNotFoundException;
import br.com.deliberation_api.application.service.dto.PautaCreateDTO;
import br.com.deliberation_api.application.service.dto.ResultResponseDTO;
import br.com.deliberation_api.application.service.dto.SessionRequestDTO;
import br.com.deliberation_api.application.service.dto.TopicUpdateRequestDTO;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.TopicEntity;
import br.com.deliberation_api.domain.model.SessionEntity;
import br.com.deliberation_api.domain.repository.TopicRepository;
import br.com.deliberation_api.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final VoteRepository voteRepository;

    public TopicService(TopicRepository topicRepository, VoteRepository voteRepository) {
        this.topicRepository = topicRepository;
        this.voteRepository = voteRepository;
    }

    public TopicEntity create(PautaCreateDTO dto) {
        TopicEntity topic = new TopicEntity(dto.getTitulo(), dto.getDescricao());
        topic.setTitle(dto.getTitulo());
        topic.setDescription(dto.getDescricao());

        return topicRepository.save(topic);
    }

    public List<TopicEntity> list() {
        return topicRepository.findAll();
    }

    public TopicEntity getById(String topicId) {
        return findTopicOrThrow(topicId);

    }

    public TopicEntity update(String topicId, TopicUpdateRequestDTO topicUpdateDto) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topicUpdateDto.title() == null && topicUpdateDto.description() == null) {
            return topic;
        }

        if (topicUpdateDto.title() != null) {
            topic.setTitle(topicUpdateDto.title());
        }

        if (topicUpdateDto.description() != null) {
            topic.setDescription(topicUpdateDto.description());
        }

        return topicRepository.save(topic);
    }

    public void delete(String topicId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        topicRepository.delete(topic);
    }

    public void openSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        TopicEntity topic = findTopicOrThrow(topicId);
        if (topic.isAvailable()) {
            throw new SessionException("Active session already exists for topicId: " + topicId);
        }

        SessionEntity sessionEntity = new SessionEntity(sessionRequestDTO.getTimeTypeOrDefault(), sessionRequestDTO.getDurationOrDefault());
        topic.setSession(sessionEntity);
        topicRepository.save(topic);
    }

    public TopicEntity closeSession(String topicId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (!topic.isAvailable()) {
            throw new SessionException("No active session found for topicId: " + topicId);
        }

        topic.getSession().setClosedManually(true);

        return topicRepository.save(topic);
    }

    public ResultResponseDTO getResult(String topicId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        long yesCount = voteRepository.countByTopicIdAndVote(topicId, VoteEnum.YES);
        long noCount = voteRepository.countByTopicIdAndVote(topicId, VoteEnum.NO);

        String result = (yesCount > noCount) ? "APPROVED" : (noCount > yesCount ? "REJECTED" : "DRAW");

        var openAt = topic.getSession() != null ? topic.getSession().getOpenAt() : null;
        var closeAt = topic.getSession() != null ? topic.getSession().getCloseAt() : null;
        return new ResultResponseDTO(topic.getId(), topic.getTitle(), topic.getDescription(), openAt, closeAt, yesCount, noCount, result);
    }

    private TopicEntity findTopicOrThrow(String topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id " + topicId));
    }
}

