package com.br.emailms.domain.service;

import com.br.emailms.application.util.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    @Autowired
    public EmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    public void enviarEmail(final com.br.compartilhado.EmissaoEmailPayloadDTO payload) {
        log.info("Iniciando processo de envio de e-mail para {}", payload.getDestinatarioEmail());
        if (EmailValidator.isValid(payload.getDestinatarioEmail())) {
            try {
                log.info("Preparando e-mail do tipo {}", payload.getTipoEmail());
                final var caminhoTemplate = "classpath:/templates/"
                        + (payload.getTipoEmail().equals("APROVACAO_CARTAO") ? "aprovado-email-template.html" : "rejeitado-email-template.html");
                final var resource = resourceLoader.getResource(caminhoTemplate);
                final var conteudo = new String(resource.getInputStream().readAllBytes(), UTF_8)
                        .replace("${nomeDestinatario}", payload.getNomeDestinatario());

                final var mensagem = mailSender.createMimeMessage();
                final var helper = new MimeMessageHelper(mensagem, true, "UTF-8");

                helper.setFrom("cartoes-ms.gmail@gmail.com");
                helper.setTo(payload.getDestinatarioEmail());
                helper.setSubject(payload.getAssunto());
                helper.setText(conteudo, true);

                log.info("Enviando e-mail para {}", payload.getDestinatarioEmail());
                mailSender.send(mensagem);
                log.info("E-mail enviado com sucesso para {}", payload.getDestinatarioEmail());
            } catch (Exception e) {
                log.error("Falha ao enviar e-mail para {}", payload.getDestinatarioEmail(), e);
                throw new RuntimeException("Falha ao enviar e-mail", e);
            }
        } else {
            log.warn("E-mail inválido: {}", payload.getDestinatarioEmail());
            throw new IllegalArgumentException("E-mail inválido: " + payload.getDestinatarioEmail());
        }
    }
}