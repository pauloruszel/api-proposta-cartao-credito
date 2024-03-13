package com.br.emailms.infrastructure.service.impl;

import com.br.emailms.infrastructure.service.EmailMessageBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class EmailMessageBuilderImpl implements EmailMessageBuilder {
    private final JavaMailSender mailSender;

    public EmailMessageBuilderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public MimeMessage construirEmail(final String remetenteEmail, final String destinatarioEmail, final String assunto, final String conteudo, final boolean isHtml) throws MessagingException {
        log.info("iniciando a construção do email de '{}' para '{}'", remetenteEmail.toLowerCase(), destinatarioEmail.toLowerCase());

        final var mensagem = mailSender.createMimeMessage();
        final var helper = new MimeMessageHelper(mensagem, true, UTF_8.name());

        helper.setFrom(remetenteEmail);
        helper.setTo(destinatarioEmail);
        helper.setSubject(assunto);
        helper.setText(conteudo, isHtml);

        log.info("email de '{}' para '{}' construído com sucesso", remetenteEmail.toLowerCase(), destinatarioEmail.toLowerCase());

        return helper.getMimeMessage();
    }
}
