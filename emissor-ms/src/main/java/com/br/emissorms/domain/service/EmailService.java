package com.br.emissorms.domain.service;

import com.br.emissorms.application.dto.PropostaPayloadDTO;
import com.br.emissorms.domain.enums.StatusEmissao;

public interface EmailService {
    void enviarEmail(com.br.compartilhado.EmissaoEmailPayloadDTO payload);

    com.br.compartilhado.EmissaoEmailPayloadDTO criarPayloadEmail(PropostaPayloadDTO propostaPayloadDTO, StatusEmissao statusEmissao);

}
