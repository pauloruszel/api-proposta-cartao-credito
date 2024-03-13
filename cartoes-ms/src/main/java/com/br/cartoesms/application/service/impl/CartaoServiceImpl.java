package com.br.cartoesms.application.service.impl;

import com.br.cartoesms.application.dto.CartaoDTO;
import com.br.cartoesms.domain.exception.CartaoNotFoundException;
import com.br.cartoesms.domain.model.Cartao;
import com.br.cartoesms.domain.model.Cliente;
import com.br.cartoesms.domain.repository.CartaoRepository;
import com.br.cartoesms.domain.service.CartaoService;
import com.br.cartoesms.domain.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartaoServiceImpl implements CartaoService {
    private final CartaoRepository repository;
    private final ModelMapper modelMapper;
    private final ClienteService clienteService;

    @Autowired
    public CartaoServiceImpl(CartaoRepository repository, ModelMapper modelMapper, ClienteService clienteService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
    }

    @Override
    public Page<CartaoDTO> listarTodosCartoesPaginados(Pageable pageable) {
        return repository.findAll(pageable).map(cartao -> modelMapper.map(cartao, CartaoDTO.class));
    }

    @Override
    public CartaoDTO obtemCartaoPorId(Long id) {
        final var cartao = repository.findById(id).orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));
        return modelMapper.map(cartao, CartaoDTO.class);
    }

    @Transactional
    @Override
    public CartaoDTO criarCartao(CartaoDTO cartaoDTO) {
        final var clienteDTO = clienteService.obtemClientePorId(cartaoDTO.getClienteId());

        final var cartao = modelMapper.map(cartaoDTO, Cartao.class);
        final var cliente = modelMapper.map(clienteDTO, Cliente.class);
        cartao.setCliente(cliente);
        final var savedCartao = repository.save(cartao);

        return modelMapper.map(savedCartao, CartaoDTO.class);
    }

    @Override
    public void deletarCartao(Long id) {
        final var cartao = repository.findById(id)
                .orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));
        repository.delete(cartao);
    }

    @Override
    public CartaoDTO atualizaCartao(Long id, CartaoDTO cartaoDTO) {
        final var existeCartao = repository.findById(id)
                .orElseThrow(() -> new CartaoNotFoundException("Cartão com id " + id + " não encontrado."));

        modelMapper.map(cartaoDTO, existeCartao);
        final var cartaoAtualizado = repository.save(existeCartao);

        return modelMapper.map(cartaoAtualizado, CartaoDTO.class);
    }
}
