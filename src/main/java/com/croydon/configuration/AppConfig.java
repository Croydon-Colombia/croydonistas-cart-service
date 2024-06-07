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

import com.croydon.Infrastructure.service.implementation.OAuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
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
                .baseUrl("https://trapirest.croydonjde.com.co/ords/api/")
                .defaultHeader("Authorization", "Basic Q1JPWURPTlJFU1Q6Q3IweWQwbiR3M2I=")
                .build();
    }

    @Bean
    public WebClient oAuthWebClient(OAuthServiceImpl oAuthService) {
        return WebClient.builder()
                .baseUrl("https://trapirest.croydonjde.com.co/ords/api/")
                .filter((request, next) -> oAuthService.getAccessToken()
                .flatMap(token -> {
                    ClientRequest newRequest = ClientRequest.from(request)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .build();
                    return next.exchange(newRequest);
                }))
                .build();
    }
}
