package com.br.emailms.application.service;

import java.util.Map;

public interface EmailTemplateManager {
    String getTemplateCaminho(String tipoEmail);
    String preencherTemplate(String templateCaminho, Map<String, String> variaveis);
}
