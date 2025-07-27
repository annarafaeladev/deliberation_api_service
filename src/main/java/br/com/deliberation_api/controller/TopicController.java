package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.service.TopicService;
import br.com.deliberation_api.application.dto.topic.TopicCreateDTO;
import br.com.deliberation_api.application.dto.topic.ResultResponseDTO;
import br.com.deliberation_api.application.dto.topic.SessionRequestDTO;
import br.com.deliberation_api.application.dto.topic.TopicUpdateDTO;
import br.com.deliberation_api.domain.model.TopicEntity;
import br.com.deliberation_api.domain.model.VoteEntity;
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
    public ResponseEntity<TopicEntity> create(@RequestBody TopicCreateDTO dto) {
        return ResponseEntity.ok(topicService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TopicEntity>> list() {
        return ResponseEntity.ok(topicService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicEntity> getById(@PathVariable String id) {
        return ResponseEntity.ok(topicService.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TopicEntity> update(@PathVariable String id, @RequestBody TopicUpdateDTO request) {
        TopicEntity updatedTopic = topicService.update(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/open-session")
    public ResponseEntity<TopicEntity> openSession(@PathVariable String id, @RequestBody(required = false) SessionRequestDTO dto) {
        TopicEntity topic = topicService.openSession(id, dto);
        return ResponseEntity.ok(topic);
    }

    @PatchMapping("/{id}/close-session")
    public ResponseEntity<TopicEntity> closeSession(@PathVariable String id) {
        TopicEntity topicEntity = topicService.closeSession(id);
        return ResponseEntity.ok(topicEntity);
    }

    @PatchMapping("/{id}/restart-session")
    public ResponseEntity<TopicEntity> restartSession(@PathVariable String id, @RequestBody(required = false) SessionRequestDTO dto) {
        TopicEntity topicEntity = topicService.restartSession(id, dto);
        return ResponseEntity.ok(topicEntity);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<ResultResponseDTO> getResult(@PathVariable String id) {
        return ResponseEntity.ok(topicService.getResult(id));
    }

    @GetMapping("/{topicId}/associates/{associateId}/vote")
    public ResponseEntity<VoteEntity> getVoteByTopicAndAssociate(@PathVariable String topicId, @PathVariable String associateId) {
        VoteEntity voteByTopicAndAssociate = topicService.getVoteByTopicAndAssociate(topicId, associateId);
        return ResponseEntity.ok(voteByTopicAndAssociate);
    }
}
