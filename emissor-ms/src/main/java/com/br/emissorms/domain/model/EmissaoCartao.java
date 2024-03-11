package com.br.emissorms.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("emissao_cartao")
@Builder
public class EmissaoCartao {

    @Id
    private String id;

    private String propostaId;

    private String cliente;

    private String dataEmissao;

    private String status;
}
