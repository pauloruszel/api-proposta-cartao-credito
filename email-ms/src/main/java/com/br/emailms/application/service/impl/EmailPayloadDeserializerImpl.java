package com.br.emailms.application.service.impl;

import com.br.emailms.application.mapper.JsonConverter;
import com.br.emailms.application.service.EmailPayloadDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailPayloadDeserializerImpl implements EmailPayloadDeserializer {
    private final JsonConverter jsonConverter;

    @Autowired
    public EmailPayloadDeserializerImpl(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public com.br.compartilhado.EmissaoEmailPayloadDTO deserialize(final String payloadJson) {
        return jsonConverter.fromJson(payloadJson, com.br.compartilhado.EmissaoEmailPayloadDTO.class);
    }
}
