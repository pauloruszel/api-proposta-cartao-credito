package com.br.emailms.infrastructure.exception;

public class JsonConverterException extends RuntimeException {
    public JsonConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}