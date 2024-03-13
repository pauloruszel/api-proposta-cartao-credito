package com.br.cartoesms.application.service.impl;

import com.br.cartoesms.application.dto.PropostaPayloadDTO;
import com.br.cartoesms.application.mapper.JsonConverter;
import com.br.cartoesms.infrastructure.messaging.PropostaKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PropostaKafkaProducerImpl implements PropostaKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final JsonConverter jsonConverter;
    private static final String TOPIC_PROPOSTA_APROVADA = "proposta_aprovada";
    private static final String TOPIC_PROPOSTA_REPROVADA = "proposta_reprovada";

    @Autowired
    public PropostaKafkaProducerImpl(KafkaTemplate<String, Object> kafkaTemplate, JsonConverter jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public void enviarPropostaAprovada(final PropostaPayloadDTO propostaDTO) {
        log.info("Iniciando envio da proposta aprovada. ID: {}", propostaDTO.getPropostaId());
        final var jsonMessage = jsonConverter.toJson(propostaDTO);
        kafkaTemplate.send(TOPIC_PROPOSTA_APROVADA, jsonMessage);
        log.info("Proposta aprovada enviada com sucesso. ID: {}", propostaDTO.getPropostaId());
    }

    @Override
    public void enviarPropostaReprovada(final PropostaPayloadDTO propostaDTO) {
        log.info("Iniciando envio da proposta reprovada. ID: {}", propostaDTO.getPropostaId());
        final var jsonMessage = jsonConverter.toJson(propostaDTO);
        kafkaTemplate.send(TOPIC_PROPOSTA_REPROVADA, jsonMessage);
        log.info("Proposta reprovada enviada com sucesso. ID: {}", propostaDTO.getPropostaId());
    }
}
