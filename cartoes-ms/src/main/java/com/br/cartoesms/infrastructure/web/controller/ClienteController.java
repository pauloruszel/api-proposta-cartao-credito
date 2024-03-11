package com.br.cartoesms.infrastructure.web.controller;

import com.br.cartoesms.application.dto.ClienteDTO;
import com.br.cartoesms.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista todos os clientes")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDTO.class)))
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        final var clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Obtém detalhes de um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Detalhes do cliente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obterClientePorId(@PathVariable Long id) {
        final var clienteDTO = clienteService.findById(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @Operation(summary = "Cria um novo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDTO.class)))
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        final var clienteDTOsalvo = clienteService.create(clienteDTO);
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteDTOsalvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(clienteDTOsalvo);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        final var updatedClienteDTO = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(updatedClienteDTO);
    }

    @Operation(summary = "Deleta um cliente por ID")
    @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
