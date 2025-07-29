package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateResponseDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.interfaces.service.AssociateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/associates")
public class AssociateController {

    private final AssociateService associateService;

    public AssociateController(AssociateService associateService) {
        this.associateService = associateService;
    }

    @PostMapping
    public ResponseEntity<AssociateResponseDTO> create(@Valid @RequestBody AssociateCreateDTO dto) {
        AssociateResponseDTO associate = associateService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(associate);
    }

    @GetMapping
    public ResponseEntity<List<AssociateResponseDTO>> list() {
        List<AssociateResponseDTO> associates = associateService.list();
        return ResponseEntity.ok(associates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociateResponseDTO> getTopicById(@PathVariable String id) {
        AssociateResponseDTO associate = associateService.getTopicById(id);
        return ResponseEntity.ok(associate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociateResponseDTO> update(@PathVariable String id, @Valid @RequestBody AssociateUpdateDTO request) {
        AssociateResponseDTO updatedTopic = associateService.update(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        associateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

