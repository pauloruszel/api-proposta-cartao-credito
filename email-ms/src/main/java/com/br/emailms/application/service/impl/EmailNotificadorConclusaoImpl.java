package com.br.emailms.application.service.impl;

import com.br.emailms.application.dto.EmailConclusaoPayloadDTO;
import com.br.emailms.application.service.EmailKafkaProducer;
import com.br.emailms.application.service.EmailNotificadorConclusao;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificadorConclusaoImpl implements EmailNotificadorConclusao {
    private final EmailKafkaProducer emailKafkaProducer;

    public EmailNotificadorConclusaoImpl(EmailKafkaProducer emailKafkaProducer) {
        this.emailKafkaProducer = emailKafkaProducer;
    }

    @Override
    public void notificarConclusao(final String destinatarioEmail) {

        final var email = EmailConclusaoPayloadDTO.builder()
                .emailCliente(destinatarioEmail)
                .statusEnvio("Concluida")
                .build();

        emailKafkaProducer.notificarEnvioConcluido(email);

    }
}
