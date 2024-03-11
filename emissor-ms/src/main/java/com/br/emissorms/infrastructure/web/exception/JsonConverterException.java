package com.br.emissorms.infrastructure.web.exception;

public class JsonConverterException extends RuntimeException {
    public JsonConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}