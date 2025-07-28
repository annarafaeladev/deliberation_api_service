package br.com.deliberation_api.controller;


import br.com.deliberation_api.application.view.dto.structure.ViewTemplateRequestDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.interfaces.service.ViewTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/views")
public class ViewTemplateController {

    private final ViewTemplateService service;

    public ViewTemplateController(ViewTemplateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ViewTemplateResponseDTO> create(@Valid @RequestBody ViewTemplateRequestDTO dto) {
        ViewTemplateResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ViewTemplateResponseDTO>> findAll() {
        List<ViewTemplateResponseDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewTemplateResponseDTO> findById(@PathVariable String id) {
        ViewTemplateResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewTemplateResponseDTO> update(@PathVariable String id,
                                                          @Valid @RequestBody ViewTemplateRequestDTO dto) {
        ViewTemplateResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

