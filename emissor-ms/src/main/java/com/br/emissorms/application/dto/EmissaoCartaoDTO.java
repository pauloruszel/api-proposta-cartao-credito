package com.br.emissorms.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmissaoCartaoDTO {
    private Long id;
    private Long propostaId;
    private Long clienteId;
    private LocalDateTime dataEmissao;
    private String status;
}
