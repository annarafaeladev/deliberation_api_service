package br.com.deliberation_api.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*Resumo das referências
Voto guarda os IDs de Pauta e Associado para manter referência.

Sessao fica embutida em Pauta para facilitar acesso.

Associado é independente.
        */
@Data
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String id;

    private String name;
    private String document;
}
