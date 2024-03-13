package com.br.cartoesms.domain.exception;

public class CartaoNotFoundException extends RuntimeException {
    public CartaoNotFoundException(String message) {
        super(message);
    }
}
