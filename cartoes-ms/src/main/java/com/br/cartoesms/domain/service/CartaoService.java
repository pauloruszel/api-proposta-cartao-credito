package com.br.cartoesms.domain.service;

import com.br.cartoesms.application.dto.CartaoDTO;
import com.br.cartoesms.infrastructure.web.exception.CartaoNotFoundException;
import com.br.cartoesms.domain.model.Cartao;
import com.br.cartoesms.domain.model.Cliente;
import com.br.cartoesms.domain.repository.CartaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartaoService {

    private final CartaoRepository repository;
    private final ModelMapper modelMapper;
    private final ClienteService clienteService;

    @Autowired
    public CartaoService(CartaoRepository repository, ModelMapper modelMapper, ClienteService clienteService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
    }

    public Page<CartaoDTO> findAll(final Pageable pageable) {
        return repository.findAll(pageable).map(cartao -> modelMapper.map(cartao, CartaoDTO.class));
    }

    public CartaoDTO findById(final Long id) {
        final var cartao = repository.findById(id).orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));
        return modelMapper.map(cartao, CartaoDTO.class);
    }

    @Transactional
    public CartaoDTO create(final CartaoDTO cartaoDTO) {
        final var clienteDTO = clienteService.findById(cartaoDTO.getClienteId());

        final var cartao = modelMapper.map(cartaoDTO, Cartao.class);
        final var cliente = modelMapper.map(clienteDTO, Cliente.class);
        cartao.setCliente(cliente);
        final var savedCartao = repository.save(cartao);

        return modelMapper.map(savedCartao, CartaoDTO.class);
    }


    @Transactional
    public void delete(final Long id) {
        final var cartao = repository.findById(id)
                .orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));
        repository.delete(cartao);
    }

    @Transactional
    public CartaoDTO update(final Long id, final CartaoDTO cartaoDTO) {
        final var existeCartao = repository.findById(id)
                .orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));

        modelMapper.map(cartaoDTO, existeCartao);
        final var cartaoAtualizado = repository.save(existeCartao);

        return modelMapper.map(cartaoAtualizado, CartaoDTO.class);
    }
}
