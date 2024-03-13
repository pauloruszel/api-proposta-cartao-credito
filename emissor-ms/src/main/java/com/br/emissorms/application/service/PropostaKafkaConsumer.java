package com.br.emissorms.application.service;

import com.br.emissorms.application.dto.PropostaPayloadDTO;
import com.br.emissorms.application.mapper.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PropostaKafkaConsumer {

    private final PropostaService propostaService;
    private final JsonConverter jsonConverter;

    @Autowired
    public PropostaKafkaConsumer(PropostaService propostaService, JsonConverter jsonConverter) {
        this.propostaService = propostaService;
        this.jsonConverter = jsonConverter;
    }

    @KafkaListener(topics = "proposta_aprovada", groupId = "grupo_emissor-ms")
    public void ouvirPropostaAprovada(final String json) {
        final var record = jsonConverter.fromJson(json, PropostaPayloadDTO.class);
        propostaService.processarProposta(record);
    }

    @KafkaListener(topics = "proposta_reprovada", groupId = "grupo_emissor-ms")
    public void ouvirPropostaReprovada(final String json) {
        final var record = jsonConverter.fromJson(json, PropostaPayloadDTO.class);
        propostaService.processarProposta(record);
    }
}
