package com.br.cartoesms.domain.service;

import com.br.cartoesms.application.dto.CartaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartaoService {
    Page<CartaoDTO> listarTodosCartoesPaginados(Pageable pageable);

    CartaoDTO obtemCartaoPorId(Long id);

    CartaoDTO criarCartao(CartaoDTO cartaoDTO);
    void deletarCartao(Long id);

    CartaoDTO atualizaCartao(Long id, CartaoDTO cartaoDTO);
}
