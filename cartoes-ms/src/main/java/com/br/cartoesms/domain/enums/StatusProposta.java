package com.br.cartoesms.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusProposta {
    PENDENTE("Pendente"),
    APROVADA("Aprovada"),
    REJEITADA("Rejeitada"),
    CONCLUIDA("Concluida");

    private final String status;

    public static StatusProposta fromString(String status) {
        for (StatusProposta sp : values()) {
            if (sp.name().equalsIgnoreCase(status)) {
                return sp;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

}
