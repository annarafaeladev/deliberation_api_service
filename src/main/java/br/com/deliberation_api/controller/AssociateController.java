package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.service.AssociateService;
import br.com.deliberation_api.application.service.dto.AssociateCreateDTO;
import br.com.deliberation_api.domain.model.AssociateEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associados")
public class AssociateController {

    private final AssociateService associateService;

    public AssociateController(AssociateService associadoService) {
        this.associateService = associadoService;
    }

    @PostMapping
    public ResponseEntity<AssociateEntity> criar(@Valid @RequestBody AssociateCreateDTO dto) {
        AssociateEntity criado = associateService.create(dto);
        return ResponseEntity.ok(criado);
    }
}

