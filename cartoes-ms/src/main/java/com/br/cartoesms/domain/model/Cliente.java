package com.br.cartoesms.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    private String endereco;
    private Double rendaMensal;
}
