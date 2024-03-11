package com.br.emailms.domain.service;

import com.br.emailms.application.dto.EmailConclusaoPayloadDTO;
import com.br.emailms.application.mapper.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailKafkaConsumer {

    private final EmailService emailService;
    private final EmailKafkaProducer emailKafkaProducer;
    private final JsonConverter jsonConverter;

    @Autowired
    public EmailKafkaConsumer(EmailService emailService, EmailKafkaProducer emailKafkaProducer, JsonConverter jsonConverter) {
        this.emailService = emailService;
        this.emailKafkaProducer = emailKafkaProducer;
        this.jsonConverter = jsonConverter;
    }

    @KafkaListener(topics = "enviar_email", groupId = "grupo-emissor-ms")
    public void receberPayloadEmail(final String payloadJson) {
        log.info("Iniciando a recepção do payload de e-mail.");
        final var record = jsonConverter.fromJson(payloadJson, com.br.compartilhado.EmissaoEmailPayloadDTO.class);
        log.info("Payload de e-mail recebido e deserializado: {}", record);

        log.info("Enviando e-mail para: {}", record.getDestinatarioEmail());
        emailService.enviarEmail(record);
        log.info("E-mail enviado com sucesso para: {}", record.getDestinatarioEmail());

        final var email = EmailConclusaoPayloadDTO.builder()
                .emailCliente(record.getDestinatarioEmail())
                .statusEnvio("Concluida")
                .build();

        log.info("Notificando conclusão do envio do e-mail.");
        emailKafkaProducer.notificarEnvioConcluido(email);
        log.info("Notificação de conclusão do envio do e-mail enviada com sucesso.");
    }
}
