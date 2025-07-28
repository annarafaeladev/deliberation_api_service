package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
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
    public ResponseEntity<AssociateEntity> create(@Valid @RequestBody AssociateCreateDTO dto) {
        AssociateEntity associate = associateService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(associate);
    }

    @GetMapping
    public ResponseEntity<List<AssociateEntity>> list() {
        List<AssociateEntity> associates = associateService.list();
        return ResponseEntity.ok(associates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociateEntity> getById(@PathVariable String id) {
        AssociateEntity associate = associateService.getById(id);
        return ResponseEntity.ok(associate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociateEntity> update(@PathVariable String id, @Valid @RequestBody AssociateUpdateDTO request) {
        AssociateEntity updatedTopic = associateService.update(id, request);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        associateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

