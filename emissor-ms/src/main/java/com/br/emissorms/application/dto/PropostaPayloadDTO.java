package com.br.emissorms.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropostaPayloadDTO {
    private Long propostaId;
    private Long clienteId;
    private String status;
    private LocalDateTime dataProposta;
    private String motivoRejeicao;
    private Double limiteCreditoAprovado;
    private String emailCliente;
    private String nomeCliente;
}
