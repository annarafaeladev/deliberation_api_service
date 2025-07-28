package br.com.deliberation_api.controller;

import br.com.deliberation_api.interfaces.service.TopicService;
import br.com.deliberation_api.interfaces.service.VoteService;
import br.com.deliberation_api.shared.exception.VoteException;
import br.com.deliberation_api.application.dto.vote.VoteRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/votes")
public class VoteController {

    private final VoteService voteService;
    private final TopicService topicService;


    public VoteController(VoteService voteService, TopicService topicService) {
        this.voteService = voteService;
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody @Valid VoteRequestDTO request
    ) {
        boolean isAvailableTopic = topicService.isValidTopicSession(request.topicId());

        if (!isAvailableTopic){
            throw new VoteException("Voting session has expired");
        }

        voteService.vote(request.topicId(), request.associateId(), request.optionId(), request.vote());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
