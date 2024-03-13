package com.br.cartoesms.domain.model;

import com.br.cartoesms.application.enums.StatusProposta;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    private String limiteCredito;

    private LocalDateTime dataProposta;

    @ManyToOne
    @JoinColumn(name = "clienteId", referencedColumnName = "id")
    private Cliente cliente;
}
