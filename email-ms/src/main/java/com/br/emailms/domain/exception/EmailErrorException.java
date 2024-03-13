package com.br.emailms.domain.exception;

public class EmailErrorException extends IllegalArgumentException {
    public EmailErrorException(String message) {
        super(message);
    }
}
