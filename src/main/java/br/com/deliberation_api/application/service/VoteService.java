package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.exception.TopicNotFoundException;
import br.com.deliberation_api.application.exception.VoteException;
import br.com.deliberation_api.application.exception.VoteNotFoundException;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.TopicEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
import br.com.deliberation_api.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteAuditService voteAuditService;

    public VoteService(VoteAuditService voteAuditService, VoteRepository voteRepository) {
        this.voteAuditService = voteAuditService;
        this.voteRepository = voteRepository;
    }

    public void vote(String topicId, String associateId, VoteEnum voteEnum) {
        Optional<VoteEntity> voteExists = voteRepository.findByTopicIdAndAssociateId(topicId, associateId);

        if (voteExists.isPresent()) {
            throw new VoteException("User has already voted and cannot vote again.");
        }

        VoteEntity vote = new VoteEntity(topicId, associateId, voteEnum);
        voteRepository.save(vote);

        voteAuditService.voteAuditCreate(vote);
    }

    public VoteEntity getByTopicIdAndAssociateId(String topicId, String associateId) {
        return voteRepository.findByTopicIdAndAssociateId(topicId, associateId)
                .orElseThrow(() -> new VoteNotFoundException(
                        String.format("Vote not found for topicId [%s] and associateId [%s]", topicId, associateId)
                ));
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

