package com.br.emailms.application.service.impl;

import com.br.compartilhado.EmissaoEmailPayloadDTO;
import com.br.emailms.domain.exception.EmailErrorException;
import com.br.emailms.domain.exception.EmailException;
import com.br.emailms.infrastructure.service.EmailMessageBuilder;
import com.br.emailms.application.service.EmailService;
import com.br.emailms.application.service.EmailTemplateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.br.emailms.application.util.EmailValidator.isValid;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailTemplateManager emailTemplateManager;
    private final JavaMailSender mailSender;
    private final EmailMessageBuilder messageBuilder;
    private static final String EMAIL_FROM = "paulo.ruszel.santos@gmail.com";

    @Autowired
    public EmailServiceImpl(EmailTemplateManager emailTemplateManager, JavaMailSender mailSender, EmailMessageBuilder messageBuilder) {
        this.emailTemplateManager = emailTemplateManager;
        this.mailSender = mailSender;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public void enviarEmail(EmissaoEmailPayloadDTO payload) {
        log.info("Iniciando processo de envio de e-mail para {}", payload.getDestinatarioEmail());
        if (isValid(payload.getDestinatarioEmail())) {
            try {
                log.info("Preparando e-mail do tipo {}", payload.getTipoEmail());

                final var caminhoTemplate = emailTemplateManager.getTemplateCaminho(payload.getTipoEmail());
                final var variaveis = Map.of("nomeDestinatario", payload.getNomeDestinatario());
                final var conteudo = emailTemplateManager.preencherTemplate(caminhoTemplate, variaveis);
                final var mimeMessage = messageBuilder.construirEmail(EMAIL_FROM, payload.getNomeDestinatario(), payload.getAssunto(), conteudo, true);

                log.info("Enviando e-mail para {}", payload.getDestinatarioEmail());
                mailSender.send(mimeMessage);
                log.info("E-mail enviado com sucesso para {}", payload.getDestinatarioEmail());
            } catch (Exception e) {
                log.error("Falha ao enviar e-mail para {}", payload.getDestinatarioEmail(), e);
                throw new EmailException("Falha ao enviar e-mail", e);
            }
        } else {
            log.warn("E-mail inválido: {}", payload.getDestinatarioEmail());
            throw new EmailErrorException("E-mail inválido: " + payload.getDestinatarioEmail());
        }
    }
}
