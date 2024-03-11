package com.br.cartoesms.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private Long id;
    private String nome;
    @CPF(message = "CPF inválido")
    private String cpf;
    @Email(regexp = ".+[@].+[\\.].+", message = "E-mail inválido")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private String endereco;
    private Double rendaMensal;
}
