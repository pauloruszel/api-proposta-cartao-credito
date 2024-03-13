package com.br.emailms.application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface EmailMessageBuilder {
    MimeMessage construirEmail(String remetenteEmail, String destinatarioEmail, String assunto, String conteudo, boolean isHtml) throws MessagingException;
}
