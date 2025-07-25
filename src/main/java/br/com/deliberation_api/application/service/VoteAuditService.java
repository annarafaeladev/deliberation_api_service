package br.com.deliberation_api.application.service;

import br.com.deliberation_api.domain.enums.VoteAuditActionEnum;
import br.com.deliberation_api.domain.enums.VoteEnum;
import br.com.deliberation_api.domain.model.VoteAuditEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
import br.com.deliberation_api.domain.repository.VoteAuditRepository;
import br.com.deliberation_api.domain.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VoteAuditService {

    private final VoteAuditRepository voteAuditRepository;

    public VoteAuditService(VoteAuditRepository voteAuditRepository, VoteRepository voteService) {
        this.voteAuditRepository = voteAuditRepository;
    }

    public void voteAuditCreate(VoteEntity vote) {
        VoteAuditEntity audit = new VoteAuditEntity(vote, VoteAuditActionEnum.CREATE);
        voteAuditRepository.save(audit);
    }

    public void voteAuditDelete(List<VoteEntity> votesToRemove) {
        List<VoteAuditEntity> audits = votesToRemove.stream()
        .map(vote -> new VoteAuditEntity(vote, VoteAuditActionEnum.DELETE
        ))
        .toList();

        voteAuditRepository.saveAll(audits);
    }

    public List<VoteAuditEntity> list(String topicId) {
        return voteAuditRepository.findByTopicId(topicId);
    }
}

