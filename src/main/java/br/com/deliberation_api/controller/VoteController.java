package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.service.VoteAuditService;
import br.com.deliberation_api.application.service.VoteService;
import br.com.deliberation_api.application.dto.vote.VoteRequestDTO;
import br.com.deliberation_api.domain.model.VoteAuditEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/votes")
public class VoteController {

    private final VoteService voteService;
    private final VoteAuditService voteAuditService;

    public VoteController(VoteService voteService, VoteAuditService voteAuditService) {
        this.voteService = voteService;
        this.voteAuditService = voteAuditService;
    }


    @GetMapping("/audit/{topicId}")
    public List<VoteAuditEntity> listAudit(@PathVariable String topicId) {
        return voteAuditService.list(topicId);
    }

    @PostMapping
    public ResponseEntity<Void> vote( @RequestBody @Valid VoteRequestDTO request
    ) {
        voteService.vote(request.topicId(), request.associateId(), request.vote());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
