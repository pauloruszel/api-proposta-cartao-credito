package com.br.emailms.domain.service;

import com.br.emailms.application.dto.EmailConclusaoPayloadDTO;
import com.br.emailms.application.mapper.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailKafkaProducer {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_EMAIL_ENVIO_CONCLUIDO = "email_envio_concluido";

    private final JsonConverter converter;

    @Autowired
    public EmailKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, JsonConverter converter) {
        this.kafkaTemplate = kafkaTemplate;
        this.converter = converter;
    }

    public void notificarEnvioConcluido(final EmailConclusaoPayloadDTO payload) {
        final var messageJson = converter.toJson(payload);
        kafkaTemplate.send(TOPIC_EMAIL_ENVIO_CONCLUIDO, messageJson);
    }

}
