package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.topic.*;
import br.com.deliberation_api.domain.model.topic.Session;
import br.com.deliberation_api.domain.model.topic.TopicEntity;
import br.com.deliberation_api.domain.model.option.VoteEntity;
import br.com.deliberation_api.interfaces.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<TopicResponseDTO> create(@Valid @RequestBody TopicCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> list() {
        return ResponseEntity.ok(topicService.listTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getByTopicId(@PathVariable String id) {
        return ResponseEntity.ok(topicService.getByTopicId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> update(@PathVariable String id, @Valid @RequestBody TopicUpdateDTO request) {
        TopicResponseDTO updatedTopic = topicService.update(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/open-session")
    public ResponseEntity<Session> openSession(@PathVariable String id, @RequestBody(required = false) SessionRequestDTO dto) {
        Session session = topicService.openSession(id, dto);
        return ResponseEntity.ok(session);
    }

    @PatchMapping("/{id}/close-session")
    public ResponseEntity<Session> closeSession(@PathVariable String id) {
        Session session = topicService.closeSession(id);
        return ResponseEntity.ok(session);
    }

    @PatchMapping("/{id}/restart-session")
    public ResponseEntity<Session> restartSession(@PathVariable String id, @RequestBody(required = false) SessionRequestDTO dto) {
        Session session = topicService.restartSession(id, dto);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/{id}/options/{optionId}")
    public ResponseEntity<OptionResponseDTO> getOption(@PathVariable String id, @PathVariable String optionId) {
        return ResponseEntity.ok(topicService.getOption(id, optionId));
    }

    @GetMapping("/{topicId}/associates/{associateId}/vote/{optionId}")
    public ResponseEntity<VoteEntity> getVoteByTopicAndAssociate(@PathVariable String topicId, @PathVariable String associateId, @PathVariable String optionId) {
        VoteEntity voteByTopicAndAssociate = topicService.getVoteByTopicAndAssociate(topicId, associateId, optionId);
        return ResponseEntity.ok(voteByTopicAndAssociate);
    }
}
