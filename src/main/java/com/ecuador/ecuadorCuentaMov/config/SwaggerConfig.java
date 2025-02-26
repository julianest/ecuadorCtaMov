package com.ecuador.ecuadorCuentaMov.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de EcuadorBank")
                        .version("1.0.0")
                        .description("Documentación de las APIs de EcuadorBank")
                );
    }
}


