package com.br.emissorms.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropostaDTO {
    private Long id;
    private Long clienteId;
    private String status;
    private LocalDateTime dataProposta;
}
