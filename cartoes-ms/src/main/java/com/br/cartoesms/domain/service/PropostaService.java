package com.br.cartoesms.domain.service;

import com.br.cartoesms.application.dto.ClienteDTO;
import com.br.cartoesms.application.dto.PropostaDTO;
import com.br.cartoesms.application.dto.PropostaPayloadDTO;
import com.br.cartoesms.domain.enums.StatusProposta;
import com.br.cartoesms.domain.model.Cliente;
import com.br.cartoesms.domain.model.Proposta;
import com.br.cartoesms.domain.repository.PropostaRepository;
import com.br.cartoesms.infrastructure.web.exception.PropostaNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.br.cartoesms.domain.service.ClienteService.getIdadeMinima;
import static java.lang.Double.valueOf;

@Slf4j
@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final ModelMapper modelMapper;

    private final ClienteService clienteService;

    private final PropostaKafkaProducer propostaKafkaProducer;

    @Autowired
    public PropostaService(PropostaRepository propostaRepository, ModelMapper modelMapper, ClienteService clienteService, PropostaKafkaProducer propostaKafkaProducer) {
        this.propostaRepository = propostaRepository;
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
        this.propostaKafkaProducer = propostaKafkaProducer;
    }

    public PropostaDTO obterPorId(final Long id) {
        final var proposta = propostaRepository.findById(id).orElseThrow(() -> new PropostaNotFoundException("Proposta com id " + id + " não encontrada"));
        return modelMapper.map(proposta, PropostaDTO.class);
    }

    public List<PropostaDTO> listarTodas() {
        final var propostas = propostaRepository.findAll();
        return propostas.stream().map(proposta -> modelMapper.map(proposta, PropostaDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PropostaDTO atualizarStatus(final Long id, final String status) {
        final var proposta = propostaRepository.findById(id).orElseThrow(() -> new PropostaNotFoundException("Proposta com id " + id + " não encontrada"));
        proposta.setStatus(StatusProposta.valueOf(status));
        propostaRepository.save(proposta);
        return modelMapper.map(proposta, PropostaDTO.class);
    }

    @Transactional
    public void atualizarStatusPorEmail(final String email, final String status) {
        final var propostas = propostaRepository.findByCliente_Email(email);
        if (propostas.isEmpty()) {
            throw new PropostaNotFoundException("Nenhuma proposta encontrada para o cliente com e-mail " + email);
        }

        propostas.forEach(proposta -> {
            proposta.setStatus(StatusProposta.valueOf(status));
            propostaRepository.save(proposta);
        });

        modelMapper.map(propostas.get(0), PropostaDTO.class);
    }

    @Transactional
    public void analisarEEnviarProposta(final PropostaDTO propostaDTO) {
        log.info("Iniciando a análise da proposta com ID: {}", propostaDTO.getId());

        final var clienteDTO = buscarCliente(propostaDTO.getClienteId());
        final var proposta = prepararProposta(propostaDTO, clienteDTO);
        final var payloadDTO = criarPayload(proposta);

        analisarCriteriosDeAprovacao(proposta, clienteDTO, payloadDTO);

        log.info("Análise da proposta com ID: {} concluída.", proposta.getId());
    }

    private ClienteDTO buscarCliente(final Long clienteId) {
        log.info("Buscando cliente com ID: {}", clienteId);
        ClienteDTO clienteDTO = clienteService.findById(clienteId);
        log.info("Cliente com ID: {} encontrado.", clienteId);
        return clienteDTO;
    }

    private Proposta prepararProposta(final PropostaDTO propostaDTO, final ClienteDTO clienteDTO) {
        log.info("Preparando proposta para o cliente com ID: {}", clienteDTO.getId());
        final var proposta = modelMapper.map(propostaDTO, Proposta.class);
        proposta.setStatus(StatusProposta.PENDENTE);
        proposta.setCliente(modelMapper.map(clienteDTO, Cliente.class));
        final var propostaSalva = propostaRepository.save(proposta);
        log.info("Proposta para o cliente com ID: {} preparada e salva com ID: {}.", clienteDTO.getId(), propostaSalva.getId());
        return propostaSalva;
    }

    private PropostaPayloadDTO criarPayload(final Proposta proposta) {
        log.info("Criando payload para a proposta com ID: {}", proposta.getId());
        final var payloadDTO = PropostaPayloadDTO.builder()
                .propostaId(proposta.getId())
                .clienteId(proposta.getCliente().getId())
                .status(String.valueOf(proposta.getStatus()))
                .dataProposta(LocalDateTime.now())
                .limiteCreditoAprovado(valueOf(proposta.getLimiteCredito()))
                .nomeCliente(proposta.getCliente().getNome())
                .emailCliente(proposta.getCliente().getEmail())
                .build();
        log.info("Payload para a proposta com ID: {} criado.", proposta.getId());
        return payloadDTO;
    }

    private void analisarCriteriosDeAprovacao(final Proposta proposta, final ClienteDTO clienteDTO, final PropostaPayloadDTO payloadDTO) {
        log.info("Iniciando análise dos critérios de aprovação para a proposta com ID: {}", proposta.getId());
        if (getIdadeMinima(clienteDTO.getDataNascimento()) >= 18 && clienteDTO.getRendaMensal() > 3000) {
            aprovarProposta(proposta, payloadDTO);
        } else {
            rejeitarProposta(proposta, payloadDTO);
        }
        propostaRepository.save(proposta);
        log.info("Análise dos critérios de aprovação para a proposta com ID: {} concluída.", proposta.getId());
    }

    private void aprovarProposta(final Proposta proposta, final PropostaPayloadDTO payloadDTO) {
        log.info("Aprovando proposta com ID: {}", proposta.getId());
        proposta.setStatus(StatusProposta.APROVADA);
        payloadDTO.setStatus("APROVADA");
        propostaKafkaProducer.enviarPropostaAprovada(payloadDTO);
        log.info("Proposta com ID: {} aprovada e enviada para o tópico de aprovação.", proposta.getId());
    }

    private void rejeitarProposta(final Proposta proposta, final PropostaPayloadDTO payloadDTO) {
        log.info("Rejeitando proposta com ID: {}", proposta.getId());
        proposta.setStatus(StatusProposta.REJEITADA);
        payloadDTO.setStatus("REJEITADA");
        payloadDTO.setMotivoRejeicao("Renda mensal não suficiente para aprovação.");
        propostaKafkaProducer.enviarPropostaReprovada(payloadDTO);
        log.info("Proposta com ID: {} rejeitada e enviada para o tópico de rejeição.", proposta.getId());
    }

}
