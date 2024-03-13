package com.br.emailms.application.service;

import com.br.emailms.application.dto.EmailConclusaoPayloadDTO;

public interface EmailKafkaProducer {
    void notificarEnvioConcluido(EmailConclusaoPayloadDTO payload);

}
