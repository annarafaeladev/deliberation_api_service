package br.com.deliberation_api.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "associate")
public class AssociateEntity {

    @Id
    private String id;

    private String nome;

    private String cpf;

    public AssociateEntity() {}

    public AssociateEntity(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}

