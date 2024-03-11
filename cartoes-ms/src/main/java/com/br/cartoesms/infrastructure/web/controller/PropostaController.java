package com.br.cartoesms.infrastructure.web.controller;

import com.br.cartoesms.application.dto.PropostaDTO;
import com.br.cartoesms.domain.service.PropostaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Slf4j
@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private final PropostaService propostaService;


    @Autowired
    public PropostaController(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @Operation(summary = "Cria uma nova proposta de crédito")
    @ApiResponse(responseCode = "202", description = "Proposta recebida e em análise")
    @PostMapping
    public ResponseEntity<String> criarProposta(@RequestBody PropostaDTO propostaDTO) {
        propostaService.analisarEEnviarProposta(propostaDTO);
        return ResponseEntity.status(ACCEPTED).body("Proposta recebida e em análise");
    }

    @Operation(summary = "Obtém uma proposta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proposta encontrada"),
            @ApiResponse(responseCode = "404", description = "Proposta não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropostaDTO> obterPropostaPorId(@PathVariable Long id) {
        final var proposta = propostaService.obterPorId(id);
        return ResponseEntity.ok(proposta);
    }

    @Operation(summary = "Lista todas as propostas")
    @ApiResponse(responseCode = "200", description = "Lista de propostas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PropostaDTO>> listarTodasPropostas() {
        final var propostas = propostaService.listarTodas();
        return ResponseEntity.ok(propostas);
    }

    @Operation(summary = "Atualiza o status de uma proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da proposta atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Proposta não encontrada para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PropostaDTO> atualizarStatusProposta(@PathVariable Long id, @RequestBody PropostaDTO propostaDTO) {
        final var propostaAtualizada = propostaService.atualizarStatus(id, propostaDTO.getStatus());
        return ResponseEntity.ok(propostaAtualizada);
    }
}
