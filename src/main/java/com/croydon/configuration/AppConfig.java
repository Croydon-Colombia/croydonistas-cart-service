/*
 * Licencia de Software para Croydon Colombia
 *
 * Copyright (c) 2024 Croydon Colombia
 *
 * Este programa es software propietario de Croydon Colombia.
 * No está permitida su distribución, copia, modificación o uso no autorizado.
 * Cualquier intento de reproducción sin consentimiento será considerado una violación de esta licencia.
 *
 * CROYDON COLOMBIA NO OTORGA GARANTÍAS EXPRESAS O IMPLÍCITAS SOBRE ESTE SOFTWARE.
 * El uso de este software implica la aceptación de los términos y condiciones establecidos.
 *
 */
package com.croydon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Configuration
public class AppConfig {
    
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                        .baseUrl("https://apirest.croydonjde.com.co/ords/api/")
                        .defaultHeader("Authorization", "Basic Q1JPWURPTlJFU1Q6Q3IweWQwblczYlIzNXQ=")
                        .build();
    }
}
