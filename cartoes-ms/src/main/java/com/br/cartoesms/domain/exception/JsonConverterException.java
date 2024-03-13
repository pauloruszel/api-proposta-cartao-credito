package com.br.cartoesms.domain.exception;

public class JsonConverterException extends RuntimeException {
    public JsonConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}