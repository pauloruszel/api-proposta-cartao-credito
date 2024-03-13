package com.br.cartoesms.application.service.impl;

import com.br.cartoesms.application.dto.ClienteDTO;
import com.br.cartoesms.domain.exception.ClienteNotFoundException;
import com.br.cartoesms.domain.model.Cliente;
import com.br.cartoesms.domain.repository.ClienteRepository;
import com.br.cartoesms.domain.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ClienteDTO> listarTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO obtemClientePorId(final Long id) {
        final var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Transactional
    @Override
    public ClienteDTO criarCliente(final ClienteDTO clienteDTO) {
        final var cliente = modelMapper.map(clienteDTO, Cliente.class);
        final var clienteSalvo = clienteRepository.save(cliente);
        return modelMapper.map(clienteSalvo, ClienteDTO.class);

    }

    @Transactional
    @Override
    public ClienteDTO atualizaCliente(final Long id, final ClienteDTO clienteDTO) {
        final var existeCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        modelMapper.map(clienteDTO, existeCliente);
        final var clienteAtualizado = clienteRepository.save(existeCliente);
        return modelMapper.map(clienteAtualizado, ClienteDTO.class);

    }

    @Override
    public void deletarCliente(final Long id) {
        final var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteDTO obtemClientePorEmail(final String email) {
        final var cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com e-mail " + email + " não encontrado."));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public int getIdadeMinima(final LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();

        int idade = hoje.getYear() - dataNascimento.getYear();

        if (hoje.getMonthValue() < dataNascimento.getMonthValue() ||
                (hoje.getMonthValue() == dataNascimento.getMonthValue() &&
                        hoje.getDayOfMonth() < dataNascimento.getDayOfMonth())) {
            idade--;
        }

        return Math.max(idade, 18);
    }

}
