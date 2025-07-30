package br.com.deliberation_api.application.service;


import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.vote.VoteEntity;
import br.com.deliberation_api.domain.repository.VoteRepository;
import br.com.deliberation_api.shared.exception.VoteException;
import br.com.deliberation_api.shared.exception.VoteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoteServiceImplTest {

    private VoteRepository voteRepository;
    private VoteServiceImpl voteService;

    @BeforeEach
    void setUp() {
        voteRepository = mock(VoteRepository.class);
        voteService = new VoteServiceImpl(voteRepository);
    }

    @Test
    void vote_ShouldSaveVote_WhenNotVotedBefore() {
        String topicId = "topic1";
        String associateId = "associate1";
        String optionId = "option1";
        VoteEnum voteEnum = VoteEnum.YES;

        when(voteRepository.findByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId))
                .thenReturn(Optional.empty());

        voteService.vote(topicId, associateId, optionId, voteEnum);

        verify(voteRepository).save(Mockito.any(VoteEntity.class));
    }

    @Test
    void vote_ShouldThrowVoteException_WhenAlreadyVoted() {
        String topicId = "topic1";
        String associateId = "associate1";
        String optionId = "option1";
        VoteEnum voteEnum = VoteEnum.NO;

        when(voteRepository.findByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId))
                .thenReturn(Optional.of(new VoteEntity(topicId, associateId, optionId, voteEnum)));

        assertThrows(VoteException.class, () ->
                voteService.vote(topicId, associateId, optionId, voteEnum)
        );

        verify(voteRepository, never()).save(any());
    }

    @Test
    void getByTopicIdAndAssociateIdAndOptionId_ShouldReturnVote_WhenFound() {
        String topicId = "topic1";
        String associateId = "associate1";
        String optionId = "option1";
        VoteEntity expectedVote = new VoteEntity(topicId, associateId, optionId, VoteEnum.YES);

        when(voteRepository.findByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId))
                .thenReturn(Optional.of(expectedVote));

        VoteEntity actualVote = voteService.getByTopicIdAndAssociateIdAndOptionId(topicId, associateId, optionId);

        assertEquals(expectedVote, actualVote);
    }

    @Test
    void getByTopicIdAndAssociateIdAndOptionId_ShouldThrowException_WhenNotFound() {
        when(voteRepository.findByTopicIdAndAssociateIdAndOptionId("t1", "a1", "o1"))
                .thenReturn(Optional.empty());

        assertThrows(VoteNotFoundException.class, () ->
                voteService.getByTopicIdAndAssociateIdAndOptionId("t1", "a1", "o1"));
    }

    @Test
    void getCountByTopicIdAndVote_ShouldReturnCorrectCount() {
        String topicId = "topic1";
        String optionId = "option1";
        VoteEnum voteEnum = VoteEnum.YES;

        when(voteRepository.countByTopicIdAndOptionIdAndVote(topicId, optionId, voteEnum))
                .thenReturn(5L);

        long count = voteService.getCountByTopicIdAndVote(topicId, optionId, voteEnum);

        assertEquals(5L, count);
    }
}
