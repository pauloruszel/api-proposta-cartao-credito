package com.br.emissorms.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("proposta")
@Builder
public class Proposta {

    @Id
    private String id;

    private String cliente;

    private String status;

    private String dataProposta;
}
