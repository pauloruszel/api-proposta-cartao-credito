package com.br.emailms.application.service;

public interface EmailService {
    void enviarEmail(com.br.compartilhado.EmissaoEmailPayloadDTO payload);
}