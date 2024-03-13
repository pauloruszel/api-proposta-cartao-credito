package com.br.cartoesms.application.service;


public interface PropostaKafkaConsumer {

    void ouvirConfirmacaoEmailConcluido(String json);
}
