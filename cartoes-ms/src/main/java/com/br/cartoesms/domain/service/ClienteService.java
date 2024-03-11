package com.br.cartoesms.domain.service;

import com.br.cartoesms.application.dto.ClienteDTO;
import com.br.cartoesms.domain.model.Cliente;
import com.br.cartoesms.domain.repository.ClienteRepository;
import com.br.cartoesms.infrastructure.web.exception.ClienteNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(final Long id) {
        final var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO create(final ClienteDTO clienteDTO) {
        final var cliente = modelMapper.map(clienteDTO, Cliente.class);
        final var clienteSalvo = clienteRepository.save(cliente);
        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO update(final Long id, final ClienteDTO clienteDTO) {
        final var existeCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        modelMapper.map(clienteDTO, existeCliente);
        final var clienteAtualizado = clienteRepository.save(existeCliente);
        return modelMapper.map(clienteAtualizado, ClienteDTO.class);
    }

    @Transactional
    public void delete(final Long id) {
        final var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id " + id + " não encontrado."));
        clienteRepository.delete(cliente);
    }

    public ClienteDTO findByEmail(final String email) {
        final var cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com e-mail " + email + " não encontrado."));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public static int getIdadeMinima(final LocalDate dataNascimento) {
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
