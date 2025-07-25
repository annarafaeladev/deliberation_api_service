package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.service.TopicService;
import br.com.deliberation_api.application.service.dto.PautaCreateDTO;
import br.com.deliberation_api.application.service.dto.ResultResponseDTO;
import br.com.deliberation_api.application.service.dto.SessionRequestDTO;
import br.com.deliberation_api.application.service.dto.TopicUpdateRequestDTO;
import br.com.deliberation_api.domain.model.TopicEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<TopicEntity> create(@RequestBody PautaCreateDTO dto) {
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
    public ResponseEntity<TopicEntity> update(@PathVariable String id, @RequestBody TopicUpdateRequestDTO request) {
        TopicEntity updatedTopic = topicService.update(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/open-session")
    public ResponseEntity<Void> openSession(@PathVariable String id, @RequestBody(required = false) SessionRequestDTO dto) {
        topicService.openSession(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/close-session")
    public ResponseEntity<TopicEntity> closeSession(@PathVariable String id) {
        TopicEntity topicEntity = topicService.closeSession(id);
        return ResponseEntity.ok(topicEntity);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<ResultResponseDTO> getResult(@PathVariable String id) {
        return ResponseEntity.ok(topicService.getResult(id));
    }
}
