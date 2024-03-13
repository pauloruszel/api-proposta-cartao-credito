package com.br.cartoesms.application.service;

import com.br.cartoesms.application.dto.ClienteDTO;
import com.br.cartoesms.application.dto.PropostaDTO;
import com.br.cartoesms.application.dto.PropostaPayloadDTO;
import com.br.cartoesms.domain.model.Proposta;

import java.util.List;

public interface PropostaService {

    PropostaDTO obterPorId(Long id);

    List<PropostaDTO> listarTodas();

    PropostaDTO atualizarStatus(Long id, String status);

    void atualizarStatusPorEmail(String email, String status);

    void analisarEEnviarProposta(PropostaDTO propostaDTO);

    Proposta prepararProposta(PropostaDTO propostaDTO, ClienteDTO clienteDTO);

    PropostaPayloadDTO criarPayload(Proposta proposta);

    void analisarCriteriosDeAprovacao(Proposta proposta, ClienteDTO clienteDTO, PropostaPayloadDTO payloadDTO);

    void aprovarProposta(Proposta proposta, PropostaPayloadDTO payloadDTO);

    void rejeitarProposta(Proposta proposta, PropostaPayloadDTO payloadDTO);

}
