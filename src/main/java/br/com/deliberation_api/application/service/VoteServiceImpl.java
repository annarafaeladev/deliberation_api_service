package br.com.deliberation_api.application.service;

import br.com.deliberation_api.interfaces.service.VoteService;
import br.com.deliberation_api.shared.exception.VoteException;
import br.com.deliberation_api.shared.exception.VoteNotFoundException;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;


    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void vote(String topicId, String associateId, String optionId, VoteEnum voteEnum) {
        Optional<VoteEntity> voteExists = voteRepository.findByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId);

        if (voteExists.isPresent()) {
            throw new VoteException("User has already voted and cannot vote again.");
        }

      VoteEntity vote = new VoteEntity(topicId, associateId, optionId, voteEnum);
        voteRepository.save(vote);
    }

    public VoteEntity getByTopicIdAndAssociateIdAndOptionId(String topicId, String associateId, String optionId) {
        return voteRepository.findByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId)
                .orElseThrow(() -> new VoteNotFoundException(
                        String.format("Vote not found for topicId [%s] and associateId [%s] and optionId [%s]", topicId, associateId, optionId)
                ));
    }


    public long getCountByTopicIdAndVote(String topicId, String optionId, VoteEnum voteEnum) {
        return voteRepository.countByTopicIdAndOptionIdAndVote(topicId, optionId, voteEnum);
    }
}

