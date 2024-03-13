package com.br.emissorms.application.service;

import com.br.emissorms.application.dto.PropostaPayloadDTO;
import com.br.emissorms.domain.enums.StatusEmissao;
import com.br.emissorms.domain.enums.StatusProposta;
import com.br.emissorms.domain.model.Proposta;
import com.br.emissorms.domain.repository.PropostaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.br.emissorms.domain.enums.StatusEmissao.APROVACAO_CARTAO;
import static com.br.emissorms.domain.enums.StatusEmissao.REJEICAO_CARTAO;
import static com.br.emissorms.domain.enums.StatusProposta.APROVADA;
import static java.lang.String.valueOf;

@Slf4j
@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final EmailService emailService;

    @Autowired
    public PropostaService(PropostaRepository propostaRepository, EmailService emailService) {
        this.propostaRepository = propostaRepository;
        this.emailService = emailService;
    }

    public void processarProposta(final PropostaPayloadDTO propostaPayloadDTO) {
        log.info("Iniciando o processamento da proposta para o cliente ID: {}", propostaPayloadDTO.getClienteId());

        final var status = StatusProposta.valueOf(propostaPayloadDTO.getStatus());
        final var proposta = criarProposta(propostaPayloadDTO, status);

        propostaRepository.save(proposta);
        log.info("Proposta {} salva com sucesso para o cliente ID: {}", status, propostaPayloadDTO.getClienteId());

        enviarEmailPorStatus(proposta, propostaPayloadDTO);
    }

    private Proposta criarProposta(final PropostaPayloadDTO propostaPayloadDTO, final StatusProposta status) {
        return Proposta.builder()
                .cliente(valueOf(propostaPayloadDTO.getClienteId()))
                .status(status.name())
                .dataProposta(valueOf(propostaPayloadDTO.getDataProposta()))
                .build();
    }

    private void enviarEmailPorStatus(final Proposta proposta, final PropostaPayloadDTO propostaPayloadDTO) {
        final var statusEmissao = getStatusEmissao(proposta.getStatus());
        final var emailPayload = emailService.criarPayloadEmail(propostaPayloadDTO, statusEmissao);

        emailService.enviarEmail(emailPayload);
        log.info("E-mail de {} enviado para o cliente ID: {}", statusEmissao, propostaPayloadDTO.getClienteId());
    }

    private StatusEmissao getStatusEmissao(final String statusProposta) {
        return APROVADA.name().equals(statusProposta) ? APROVACAO_CARTAO : REJEICAO_CARTAO;
    }
}