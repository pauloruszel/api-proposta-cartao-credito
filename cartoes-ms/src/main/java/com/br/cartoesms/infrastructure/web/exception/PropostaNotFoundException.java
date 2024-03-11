package com.br.cartoesms.infrastructure.web.exception;

public class PropostaNotFoundException extends RuntimeException {
    public PropostaNotFoundException(String message) {
        super(message);
    }
}
