package com.br.emailms.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailKafkaConsumer {

    private final EmailService emailService;
    private final EmailPayloadDeserializer payloadDeserializer;
    private final EmailNotificadorConclusao notificadorConclusao;

    public EmailKafkaConsumer(EmailService emailService, EmailPayloadDeserializer payloadDeserializer, EmailNotificadorConclusao notificadorConclusao) {
        this.emailService = emailService;
        this.payloadDeserializer = payloadDeserializer;
        this.notificadorConclusao = notificadorConclusao;
    }

    @KafkaListener(topics = "enviar_email", groupId = "grupo-emissor-ms")
    public void receberPayloadEmail(final String payloadJson) {
        log.info("Iniciando a recepção do payload de e-mail.");
        final var record = payloadDeserializer.deserialize(payloadJson);
        log.info("Payload de e-mail recebido e deserializado: {}", record);

        log.info("Enviando e-mail para: {}", record.getDestinatarioEmail());
        emailService.enviarEmail(record);
        log.info("E-mail enviado com sucesso para: {}", record.getDestinatarioEmail());

        log.info("Notificando conclusão do envio do e-mail.");
        notificadorConclusao.notificarConclusao(record.getDestinatarioEmail());
        log.info("Notificação de conclusão do envio do e-mail enviada com sucesso.");
    }
}