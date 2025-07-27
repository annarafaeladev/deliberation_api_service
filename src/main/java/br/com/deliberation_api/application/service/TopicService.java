package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.exception.SessionException;
import br.com.deliberation_api.application.exception.TopicNotFoundException;
import br.com.deliberation_api.application.dto.topic.TopicCreateDTO;
import br.com.deliberation_api.application.dto.topic.ResultResponseDTO;
import br.com.deliberation_api.application.dto.topic.SessionRequestDTO;
import br.com.deliberation_api.application.dto.topic.TopicUpdateDTO;
import br.com.deliberation_api.domain.enums.ResultEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.AssociateEntity;
import br.com.deliberation_api.domain.model.TopicEntity;
import br.com.deliberation_api.domain.model.SessionEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
import br.com.deliberation_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final VoteService voteService;
    private final AssociateService associateService;

    public TopicService(TopicRepository topicRepository, VoteService voteService, AssociateService associateService) {
        this.topicRepository = topicRepository;
        this.voteService = voteService;
        this.associateService = associateService;
    }

    public TopicEntity create(TopicCreateDTO dto) {
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

    public TopicEntity update(String topicId, TopicUpdateDTO topicUpdateDto) {
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

    public TopicEntity openSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topic.getSession() != null) {
            throw new SessionException("Active session already exists for topicId: " + topicId);
        }

        SessionEntity sessionEntity = new SessionEntity(sessionRequestDTO.getTimeTypeOrDefault(), sessionRequestDTO.getDurationOrDefault());
        topic.setSession(sessionEntity);
        topicRepository.save(topic);

        return topic;
    }


    public TopicEntity restartSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topic.getSession() == null) {
            throw new SessionException("Not found Session for topicId: " + topicId);
        }

        if (!topic.isAvailable()) {
            throw new SessionException("Session no permission restart time for topicId: " + topicId);
        }

        try {
            voteService.audit(topicId);

            topic.getSession().start(
                    sessionRequestDTO.getTimeTypeOrDefault(),
                    sessionRequestDTO.getDurationOrDefault()
            );

            topicRepository.save(topic);

            return topic;
        } catch (Exception ex) {
            throw  new SessionException("The session could not be restarted.");
        }
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

        long yesCount = voteService.getCountByTopicIdAndVote(topicId, VoteEnum.YES);
        long noCount = voteService.getCountByTopicIdAndVote(topicId, VoteEnum.NO);

        String result = (yesCount > noCount) ? ResultEnum.APPROVED.name() : (noCount > yesCount ? ResultEnum.REJECT.name() : ResultEnum.DRAW.name());

        var openAt = topic.getSession() != null ? topic.getSession().getOpenAt() : null;
        var closeAt = topic.getSession() != null ? topic.getSession().getCloseAt() : null;
        return new ResultResponseDTO(topic.getId(), topic.getTitle(), topic.getDescription(), openAt, closeAt, yesCount, noCount, result);
    }


    private TopicEntity findTopicOrThrow(String topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id " + topicId));
    }

    public VoteEntity getVoteByTopicAndAssociate(String topicId, String associateId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        AssociateEntity associate = associateService.getById(associateId);

        return voteService.getByTopicIdAndAssociateId(topic.getId(), associate.getId());
    }
}

