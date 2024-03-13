package com.br.emailms.application.service;


public interface EmailPayloadDeserializer {

    com.br.compartilhado.EmissaoEmailPayloadDTO deserialize(String payloadJson);
}
