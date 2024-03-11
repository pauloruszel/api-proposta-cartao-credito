package com.br.cartoesms.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoDTO {

    private Long id;
    private String numeroCartao;
    private String nomeTitular;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataValidade;
    private String cvv;
    private Boolean ativo;
    private LocalDateTime dataEmissao;
    private Long clienteId;
}
