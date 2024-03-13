package com.br.cartoesms.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        final var extensions = new HashMap<String, Object>();
        extensions.put("showExtensions", swaggerUiConfigProperties.getShowExtensions());

        return new OpenAPI()
                .info(new Info().title("Serviço de Gerenciamento de Cartões de Crédito")
                        .description("Esta API é responsável pela gestão do ciclo de vida dos cartões de crédito, " +
                                "incluindo a solicitação de novos cartões, análise e aprovação de propostas de crédito, " +
                                "bem como a emissão e ativação de cartões.")
                        .version("1.0.0"))
                .addServersItem(new Server().url("http://localhost:8080").description("Ambiente Local"))
                .components(new io.swagger.v3.oas.models.Components())
                .extensions(extensions);
    }

}