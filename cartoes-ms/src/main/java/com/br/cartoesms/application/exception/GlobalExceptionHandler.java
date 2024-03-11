package com.br.cartoesms.application.exception;

import com.br.cartoesms.infrastructure.web.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoNotFoundException.class)
    public ResponseEntity<ApiError> handleCartaoNotFound(CartaoNotFoundException ex) {
        log.error("Erro: {}", ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ApiError> handleClienteNotFound(ClienteNotFoundException ex) {
        log.error("Erro: {}", ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiError> handleInvalidData(InvalidDataException ex) {
        log.error("Erro: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(PropostaNotFoundException.class)
    public ResponseEntity<ApiError> handlePropostaNotFound(PropostaNotFoundException ex) {
        log.error("Erro: {}", ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ex.getMessage()));
    }
}