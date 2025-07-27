package br.com.deliberation_api.interfaces.service;

import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.option.VoteEntity;

public interface VoteService {
    void vote(String topicId, String associateId, String optionId, VoteEnum voteEnum);

    VoteEntity getByTopicIdAndAssociateIdAndOptionId(String topicId, String associateId, String optionId);

    long getCountByTopicIdAndVote(String topicId, String optionId, VoteEnum voteEnum);
}
