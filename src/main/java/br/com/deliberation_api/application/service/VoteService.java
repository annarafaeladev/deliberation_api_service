package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.exception.SessionException;
import br.com.deliberation_api.application.exception.TopicNotFoundException;
import br.com.deliberation_api.application.service.dto.PautaCreateDTO;
import br.com.deliberation_api.application.service.dto.ResultResponseDTO;
import br.com.deliberation_api.application.service.dto.SessionRequestDTO;
import br.com.deliberation_api.application.service.dto.TopicUpdateRequestDTO;
import br.com.deliberation_api.domain.enums.VoteAuditActionEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.SessionEntity;
import br.com.deliberation_api.domain.model.TopicEntity;
import br.com.deliberation_api.domain.model.VoteAuditEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
import br.com.deliberation_api.domain.repository.TopicRepository;
import br.com.deliberation_api.domain.repository.VoteAuditRepository;
import br.com.deliberation_api.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteAuditService voteAuditService;

    public VoteService(VoteAuditService voteAuditService, VoteRepository voteRepository) {
        this.voteAuditService = voteAuditService;
        this.voteRepository = voteRepository;
    }

    public void vote(String topicId, String associateId, VoteEnum voteEnum) {
        VoteEntity vote = new VoteEntity(topicId, associateId, voteEnum);
        voteRepository.save(vote);

        voteAuditService.voteAuditCreate(vote);
    }

    public void audit(String topicId) {
        List<VoteEntity> votesToRemove = voteRepository.findByTopicId(topicId);

        voteAuditService.voteAuditDelete(votesToRemove);
        voteRepository.deleteAll(votesToRemove);
    }

    public long getCountByTopicIdAndVote(String topicId, VoteEnum voteEnum) {
        return voteRepository.countByTopicIdAndVote(topicId, voteEnum);
    }
}

