package com.br.emailms.application.service.impl;

import com.br.emailms.application.service.EmailTemplateManager;
import com.br.emailms.domain.exception.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class EmailTemplateManagerImpl implements EmailTemplateManager {
    private final ResourceLoader resourceLoader;

    public EmailTemplateManagerImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String getTemplateCaminho(final String tipoEmail) {
        log.info("iniciando a busca pelo caminho do template de email para o tipo: {}", tipoEmail.toLowerCase());

        final var caminho = "classpath:/templates/"
                + (tipoEmail.equals("APROVACAO_CARTAO") ?
                "aprovado-email-template.html" : "rejeitado-email-template.html");

        log.info("finalizando a busca pelo caminho do template com o caminho encontrado: {}", caminho.toLowerCase());
        return caminho;
    }

    @Override
    public String preencherTemplate(final String templateCaminho, final Map<String, String> variaveis) {
        log.info("iniciando o preenchimento do template no caminho: {}", templateCaminho.toLowerCase());
        try {
            final var resource = resourceLoader.getResource(templateCaminho);
            var template = new String(resource.getInputStream().readAllBytes(), UTF_8);

            for (Map.Entry<String, String> variavel : variaveis.entrySet()) {
                template = template.replace("${" + variavel.getKey() + "}", variavel.getValue());
            }
            log.info("finalizando o preenchimento do template.");
            return template;
        } catch (IOException e) {
            log.error("erro ao carregar o template de email", e);
            throw new EmailException("Erro ao carregar o template de email", e);
        }
    }
}
