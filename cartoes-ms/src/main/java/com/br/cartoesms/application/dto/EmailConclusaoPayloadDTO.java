package com.br.cartoesms.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConclusaoPayloadDTO {
    private String emailCliente;
    private String statusEnvio;
}
