package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.dto.topic.*;
import br.com.deliberation_api.interfaces.service.TopicService;
import br.com.deliberation_api.shared.exception.OptionNotFoundException;
import br.com.deliberation_api.shared.exception.SessionException;
import br.com.deliberation_api.shared.exception.TopicNotFoundException;
import br.com.deliberation_api.shared.enums.ResultEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.model.option.OptionEntity;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.domain.repository.OptionRepository;
import br.com.deliberation_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final VoteServiceImpl voteService;
    private final AssociateServiceImpl associateService;
    private final OptionRepository optionRepository;

    public TopicServiceImpl(TopicRepository topicRepository, VoteServiceImpl voteService, AssociateServiceImpl associateService, OptionRepository optionRepository) {
        this.topicRepository = topicRepository;
        this.voteService = voteService;
        this.associateService = associateService;
        this.optionRepository = optionRepository;
    }

    public TopicResponseDTO create(TopicCreateDTO dto) {
        List<OptionEntity> options = dto.getOptions().stream()
                .map(o -> new OptionEntity(o.getTitle(), o.getDescription()))
                .collect(Collectors.toList());

        List<OptionEntity> savedOptions = optionRepository.saveAll(options);

        TopicEntity topic = new TopicEntity(dto.getTitle(), dto.getDescription());
        topic.setOptions(savedOptions);

        TopicEntity save = topicRepository.save(topic);

        return convertToResponseDTO(save);
    }


    public List<TopicEntity> list() {
        return topicRepository.findAll();
    }

    public List<TopicResponseDTO> listTopics() {
        List<TopicEntity> all = topicRepository.findAll();

        return all.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TopicResponseDTO getByTopicId(String topicId) {
        TopicEntity topicEntity = findTopicOrThrow(topicId);

        return convertToResponseDTO(topicEntity);
    }

    public  TopicEntity getById(String topicId) {
        return findTopicOrThrow(topicId);
    }


    public boolean isValidTopicSession(String topicId) {
        return findTopicOrThrow(topicId).isAvailable();
    }

    public TopicResponseDTO update(String topicId, TopicUpdateDTO topicUpdateDto) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topicUpdateDto.title() == null && topicUpdateDto.description() == null && topicUpdateDto.options() == null) {
            return convertToResponseDTO(topic);
        }

        if (topicUpdateDto.title() != null) {
            topic.setTitle(topicUpdateDto.title());
        }

        if (topicUpdateDto.description() != null) {
            topic.setDescription(topicUpdateDto.description());
        }

        if (topicUpdateDto.options() != null) {
            List<OptionEntity> options = topicUpdateDto.options().stream()
                    .map(option -> {
                        OptionEntity optionUpdate = optionRepository.findById(option.getId())
                                .orElseThrow(() -> new OptionNotFoundException("Option not found with id " + option.getId()));

                        optionUpdate.setTitle(option.getTitle());
                        optionUpdate.setDescription(option.getDescription());

                        return optionUpdate;
                    })
                    .collect(Collectors.toList());

            optionRepository.saveAll(options);
            topic.setOptions(options);
        }

        TopicEntity save = topicRepository.save(topic);

        return convertToResponseDTO(save);
    }

    public void delete(String topicId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topic.getOptions() != null && !topic.getOptions().isEmpty()) {
            optionRepository.deleteAll(topic.getOptions());
        }

        topicRepository.delete(topic);
    }


    public Session openSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topic.getSession() != null) {
            throw new SessionException("Active session already exists for topicId: " + topicId);
        }

        Session sessionEntity = new Session(sessionRequestDTO.getTimeType(), sessionRequestDTO.getDuration());
        topic.setSession(sessionEntity);
        topicRepository.save(topic);

        return sessionEntity;
    }


    public Session restartSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (topic.getSession() == null) {
            throw new SessionException("Not found Session for topicId: " + topicId);
        }

        if (!topic.isAvailable()) {
            throw new SessionException("Session no permission restart time for topicId: " + topicId);
        }

        try {
            topic.getSession().start(
                    sessionRequestDTO.getTimeType(),
                    sessionRequestDTO.getDuration()
            );

            topicRepository.save(topic);

            return topic.getSession();
        } catch (Exception ex) {
            throw  new SessionException("The session could not be restarted.");
        }
    }

    public Session closeSession(String topicId) {
        TopicEntity topic = findTopicOrThrow(topicId);

        if (!topic.isAvailable()) {
            throw new SessionException("No active session found for topicId: " + topicId);
        }

        topic.getSession().setClosedManually(true);

        topicRepository.save(topic);

        return topic.getSession();
    }

    public OptionResponseDTO getOption(String topicId, String optionId) {
        TopicEntity topic = findTopicOrThrow(topicId);
        OptionEntity option = findOptionOrThrow(optionId);

        long yesCount = voteService.getCountByTopicIdAndVote(topicId, optionId, VoteEnum.YES);
        long noCount = voteService.getCountByTopicIdAndVote(topicId, optionId, VoteEnum.NO);

        String result = (yesCount > noCount) ? ResultEnum.APPROVED.name() : (noCount > yesCount ? ResultEnum.REJECT.name() : ResultEnum.DRAW.name());

        var openAt = topic.getSession() != null ? topic.getSession().getOpenAt() : null;
        var closeAt = topic.getSession() != null ? topic.getSession().getCloseAt() : null;
        return new OptionResponseDTO(topic.getId(), topic.getTitle(), topic.getDescription(), openAt, closeAt, option.getId(), option.getTitle(), option.getDescription(), yesCount, noCount, result);
    }


    private TopicEntity findTopicOrThrow(String topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id " + topicId));
    }

    private OptionEntity findOptionOrThrow(String optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException("Option not found with id " + optionId));
    }

    public VoteEntity getVoteByTopicAndAssociate(String topicId, String associateId, String optionId) {
        TopicEntity topic = findTopicOrThrow(topicId);
        OptionEntity option = findOptionOrThrow(optionId);

        AssociateEntity associate = associateService.getById(associateId);

        return voteService.getByTopicIdAndAssociateIdAndOptionId(topic.getId(), associate.getId(), option.getId());
    }

    private TopicResponseDTO convertToResponseDTO(TopicEntity topic) {
        return new TopicResponseDTO(topic);
    }
}

