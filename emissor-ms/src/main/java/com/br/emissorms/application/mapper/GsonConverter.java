package com.br.emissorms.application.mapper;

import com.br.emissorms.domain.exception.JsonConverterException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GsonConverter {

    private final Gson gson;

    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            throw new JsonConverterException("Erro ao converter objeto para JSON", e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            throw new JsonConverterException("Erro ao converter JSON para objeto", e);
        }
    }

}