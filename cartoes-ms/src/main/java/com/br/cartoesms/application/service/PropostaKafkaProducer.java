package com.br.cartoesms.application.service;


import com.br.cartoesms.application.dto.PropostaPayloadDTO;

public interface PropostaKafkaProducer {

    void enviarPropostaAprovada(PropostaPayloadDTO propostaDTO);

    void enviarPropostaReprovada(PropostaPayloadDTO propostaDTO);
}
