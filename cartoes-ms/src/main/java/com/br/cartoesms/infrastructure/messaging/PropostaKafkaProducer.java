package com.br.cartoesms.infrastructure.messaging;


import com.br.cartoesms.application.dto.PropostaPayloadDTO;

public interface PropostaKafkaProducer {

    void enviarPropostaAprovada(PropostaPayloadDTO propostaDTO);

    void enviarPropostaReprovada(PropostaPayloadDTO propostaDTO);
}
