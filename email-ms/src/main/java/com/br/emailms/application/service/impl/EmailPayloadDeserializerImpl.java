package com.br.emailms.application.service.impl;

import com.br.emailms.application.mapper.GsonConverter;
import com.br.emailms.application.service.EmailPayloadDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailPayloadDeserializerImpl implements EmailPayloadDeserializer {
    private final GsonConverter gsonConverter;

    @Autowired
    public EmailPayloadDeserializerImpl(GsonConverter gsonConverter) {
        this.gsonConverter = gsonConverter;
    }

    @Override
    public com.br.compartilhado.EmissaoEmailPayloadDTO deserialize(final String payloadJson) {
        return gsonConverter.fromJson(payloadJson, com.br.compartilhado.EmissaoEmailPayloadDTO.class);
    }
}
