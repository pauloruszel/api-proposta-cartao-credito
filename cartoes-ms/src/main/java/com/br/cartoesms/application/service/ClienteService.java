package com.br.cartoesms.application.service;

import com.br.cartoesms.application.dto.ClienteDTO;

import java.time.LocalDate;
import java.util.List;

public interface ClienteService {

    List<ClienteDTO> listarTodosClientes();

    ClienteDTO obtemClientePorId(Long id);

    ClienteDTO criarCliente(ClienteDTO clienteDTO);

    ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO);

    void deletarCliente(Long id);

    ClienteDTO obtemClientePorEmail(String email);

    int getIdadeMinima(LocalDate dataNascimento);
}
