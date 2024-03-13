package com.br.cartoesms.infrastructure.messaging;


public interface PropostaKafkaConsumer {

    void ouvirConfirmacaoEmailConcluido(String json);
}
