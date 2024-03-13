package com.br.emailms.domain.service;

public interface EmailService {
    void enviarEmail(com.br.compartilhado.EmissaoEmailPayloadDTO payload);
}