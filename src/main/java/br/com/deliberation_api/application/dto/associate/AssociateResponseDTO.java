package br.com.deliberation_api.application.dto.associate;

import br.com.deliberation_api.domain.model.associate.AssociateEntity;

import lombok.Data;

@Data
public class AssociateResponseDTO{
    private String id;
    private String name;
    private String document;

    public AssociateResponseDTO(){}

    public AssociateResponseDTO(AssociateEntity associate) {
        this.id = associate.getId();
        this.name = associate.getName();
        this.document = associate.getDocument();
    }

    public AssociateResponseDTO(String id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }
}

