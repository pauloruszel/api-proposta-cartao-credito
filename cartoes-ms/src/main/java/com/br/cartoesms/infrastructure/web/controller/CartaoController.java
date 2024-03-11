package com.br.cartoesms.infrastructure.web.controller;

import com.br.cartoesms.application.dto.CartaoDTO;
import com.br.cartoesms.domain.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Slf4j
@RestController
@RequestMapping("/api/v1/cartoes")
public class CartaoController {
    private final CartaoService cartaoService;

    @Autowired
    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @Operation(summary = "Lista todos os cartões com paginação")
    @ApiResponse(responseCode = "200", description = "Cartões listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<CartaoDTO>> listarTodosCartoes(Pageable pageable) {
        final var cartoesPage = cartaoService.findAll(pageable);
        return ResponseEntity.ok(cartoesPage);
    }

    @Operation(summary = "Obtém detalhes de um cartão por ID")
    @ApiResponse(responseCode = "200", description = "Detalhes do cartão",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartaoDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CartaoDTO> obterCartaoPorId(@PathVariable Long id) {
        final var cartaoDTO = cartaoService.findById(id);
        return ResponseEntity.ok(cartaoDTO);
    }

    @Operation(summary = "Cria um novo cartão para um cliente existente")
    @ApiResponse(responseCode = "200", description = "Cartão criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartaoDTO.class)))
    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoDTO cartaoDTO) {
        final var cartaoDTOsalvo = cartaoService.create(cartaoDTO);
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cartaoDTOsalvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(cartaoDTOsalvo);
    }

    @Operation(summary = "Atualiza um cartão existente")
    @ApiResponse(responseCode = "200", description = "Cartão atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartaoDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<CartaoDTO> atualizarCartao(@PathVariable Long id, @RequestBody CartaoDTO cartaoDTO) {
        final var cartaoDTOatualizado = cartaoService.update(id, cartaoDTO);
        return ResponseEntity.ok(cartaoDTOatualizado);
    }

    @Operation(summary = "Deleta um cartão por ID")
    @ApiResponse(responseCode = "204", description = "Cartão deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
