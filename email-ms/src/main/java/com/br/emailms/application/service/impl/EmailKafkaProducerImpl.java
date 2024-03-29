package com.br.emailms.application.service.impl;

import com.br.emailms.application.dto.EmailConclusaoPayloadDTO;
import com.br.emailms.application.mapper.GsonConverter;
import com.br.emailms.application.service.EmailKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailKafkaProducerImpl implements EmailKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final GsonConverter gsonConverter;
    private static final String TOPIC_EMAIL_ENVIO_CONCLUIDO = "email_envio_concluido";

    @Autowired
    public EmailKafkaProducerImpl(KafkaTemplate<String, Object> kafkaTemplate, GsonConverter gsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.gsonConverter = gsonConverter;
    }

    @Override
    public void notificarEnvioConcluido(final EmailConclusaoPayloadDTO payload) {
        log.info("Iniciando notificação de envio concluído para o payload: {}", payload);

        final var messageJson = gsonConverter.toJson(payload);
        log.debug("Payload convertido para JSON: {}", messageJson);

        kafkaTemplate.send(TOPIC_EMAIL_ENVIO_CONCLUIDO, messageJson);
        log.info("Notificação de envio concluído enviada ao tópico: {}", TOPIC_EMAIL_ENVIO_CONCLUIDO);
    }
}
