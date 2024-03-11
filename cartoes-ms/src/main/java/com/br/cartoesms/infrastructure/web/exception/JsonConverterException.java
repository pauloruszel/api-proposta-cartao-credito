package com.br.cartoesms.infrastructure.web.exception;

public class JsonConverterException extends RuntimeException {
    public JsonConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}