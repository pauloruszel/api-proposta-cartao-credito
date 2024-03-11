package com.br.cartoesms.domain.service;


import com.br.cartoesms.application.dto.PropostaPayloadDTO;
import com.br.cartoesms.application.mapper.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PropostaKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final JsonConverter jsonConverter;
    private static final String TOPIC_PROPOSTA_APROVADA = "proposta_aprovada";
    private static final String TOPIC_PROPOSTA_REPROVADA = "proposta_reprovada";

    @Autowired
    public PropostaKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, JsonConverter jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    public void enviarPropostaAprovada(final PropostaPayloadDTO propostaDTO) {
        final var jsonMessage = jsonConverter.toJson(propostaDTO);
        kafkaTemplate.send(TOPIC_PROPOSTA_APROVADA, jsonMessage);
    }

    public void enviarPropostaReprovada(final PropostaPayloadDTO propostaDTO) {
        final var jsonMessage = jsonConverter.toJson(propostaDTO);
        kafkaTemplate.send(TOPIC_PROPOSTA_REPROVADA, jsonMessage);
    }
}
