package com.br.emissorms.application.service;

import com.br.emissorms.application.dto.PropostaPayloadDTO;
import com.br.emissorms.application.mapper.JsonConverter;
import com.br.emissorms.domain.enums.StatusEmissao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final JsonConverter jsonConverter;

    private static final String TOPIC_ENVIAR_EMAIL = "enviar_email";

    @Autowired
    public EmailServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, JsonConverter jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public void enviarEmail(final com.br.compartilhado.EmissaoEmailPayloadDTO payload) {
        log.info("inicio do envio da informações de e-mail para o tópico {}", TOPIC_ENVIAR_EMAIL);
        final var jsonMessage = jsonConverter.toJson(payload);
        kafkaTemplate.send(TOPIC_ENVIAR_EMAIL, jsonMessage);
        log.info("fim do envio da informações de e-mail para o tópico {} com o Json {}", TOPIC_ENVIAR_EMAIL, jsonMessage);
    }

    @Override
    public com.br.compartilhado.EmissaoEmailPayloadDTO criarPayloadEmail(final PropostaPayloadDTO propostaPayloadDTO, final StatusEmissao statusEmissao) {
        String assunto = "";
        String corpoMensagem = switch (statusEmissao) {
            case APROVACAO_CARTAO -> {
                assunto = "Aprovação de Proposta";
                yield "Parabéns! Sua proposta foi aprovada.";
            }
            case REJEICAO_CARTAO -> {
                assunto = "Reprovação de Proposta";
                yield "Infelizmente, sua proposta não pôde ser aprovada.";
            }
            default -> "";
        };

        return com.br.compartilhado.EmissaoEmailPayloadDTO.builder()
                .destinatarioEmail(propostaPayloadDTO.getEmailCliente())
                .assunto(assunto)
                .corpoMensagem(corpoMensagem)
                .nomeDestinatario(propostaPayloadDTO.getNomeCliente())
                .tipoEmail(statusEmissao.name())
                .build();
    }
}
