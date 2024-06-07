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
package com.croydon.Infrastructure.service.implementation;

import com.croydon.Infrastructure.dto.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class OAuthServiceImpl {

    @Autowired
    private final WebClient webClient;

    public OAuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://trapirest.croydonjde.com.co/ords/api/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public Mono<String> getAccessToken() {
        return webClient.post()
                .uri("oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic V21RbDNHQUhnNEF3cDBwUl82VUZxZy4uOkdINTFfdXVKYi1fdmxlV04xZDl4S2cuLg==")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .map(OAuthTokenResponse::getAccessToken);
    }
}
