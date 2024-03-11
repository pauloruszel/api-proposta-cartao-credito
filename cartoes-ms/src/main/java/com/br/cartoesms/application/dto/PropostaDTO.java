package com.br.cartoesms.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropostaDTO {

    private Long id;
    private String status;
    private String limiteCredito;
    private LocalDateTime dataProposta;
    private Long clienteId;
}
