package com.br.cartoesms.application.service.impl;

import com.br.cartoesms.application.dto.EmailConclusaoPayloadDTO;
import com.br.cartoesms.application.enums.StatusProposta;
import com.br.cartoesms.application.mapper.JsonConverter;
import com.br.cartoesms.domain.service.PropostaService;
import com.br.cartoesms.infrastructure.messaging.PropostaKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PropostaKafkaConsumerImpl implements PropostaKafkaConsumer {
    private final PropostaService propostaService;
    private final JsonConverter jsonConverter;
    private static final String TOPIC_ENVIO_EMAIL_CONCLUIDO = "email_envio_concluido";

    @Autowired
    public PropostaKafkaConsumerImpl(PropostaService propostaService, JsonConverter jsonConverter) {
        this.propostaService = propostaService;
        this.jsonConverter = jsonConverter;
    }


    @Override
    @KafkaListener(topics = TOPIC_ENVIO_EMAIL_CONCLUIDO, groupId = "grupo-cartoes-ms")
    public void ouvirConfirmacaoEmailConcluido(String json) {
        final var record = jsonConverter.fromJson(json, EmailConclusaoPayloadDTO.class);
        log.info("Inicio da operação de confirmação de envio de e-mail concluído para emailCliente: {}", record.getEmailCliente());

        if (record.getEmailCliente() != null) {
            log.info("Iniciando atualização do status do cliente com e-mail: {}", record.getEmailCliente());
            try {
                propostaService.atualizarStatusPorEmail(record.getEmailCliente(), StatusProposta.fromString("Concluida").getStatus());
                log.info("Status da proposta com id: {} foi atualizado com sucesso para CONCLUÍDA", record.getEmailCliente());
            } catch (Exception e) {
                log.error("Falha ao atualizar o status do cliente com e-mail: {}. Detalhes do erro: {}", record.getEmailCliente(), e.getMessage());
            }
        } else {
            log.warn("Dados recebidos no tópico {} são inválidos ou insuficientes: {}", TOPIC_ENVIO_EMAIL_CONCLUIDO, record);
        }

        log.info("Fim da operação de confirmação de envio de e-mail concluído do cliente com e-mail: {}", record.getEmailCliente() != null ? record.getEmailCliente() : "null");
    }
}
