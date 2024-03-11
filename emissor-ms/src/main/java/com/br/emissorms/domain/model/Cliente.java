package com.br.emissorms.domain.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("clientes")
@Builder
public class Cliente {

    @Id
    private String id;

    private String nome;

    private String email;

}
