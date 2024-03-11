package com.br.cartoesms.domain.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private String nomeTitular;
    private LocalDate dataValidade;
    private String cvv;
    private Boolean ativo;
    private LocalDateTime dataEmissao;

    @ManyToOne
    @JoinColumn(name = "clienteId", referencedColumnName = "id")
    private Cliente cliente;

}
