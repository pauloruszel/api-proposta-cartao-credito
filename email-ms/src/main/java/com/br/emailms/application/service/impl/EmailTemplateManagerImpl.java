package com.br.emailms.application.service.impl;

import com.br.emailms.application.service.EmailTemplateManager;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EmailTemplateManagerImpl implements EmailTemplateManager {
    private final ResourceLoader resourceLoader;

    public EmailTemplateManagerImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String getTemplateCaminho(final String tipoEmail) {
        return "classpath:/templates/"
                + (tipoEmail.equals("APROVACAO_CARTAO") ?
                "aprovado-email-template.html" : "rejeitado-email-template.html");
    }

    @Override
    public String preencherTemplate(final String templateCaminho, final Map<String, String> variaveis) {
        try {
            final var resource = resourceLoader.getResource(templateCaminho);
            var template = new String(resource.getInputStream().readAllBytes(), UTF_8);

            for (Map.Entry<String, String> variavel : variaveis.entrySet()) {
                template = template.replace("${" + variavel.getKey() + "}", variavel.getValue());
            }
            return template;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o template de email", e);
        }
    }
}
