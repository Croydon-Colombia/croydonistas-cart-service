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
package com.croydon.Infrastructure;

import com.croydon.Infrastructure.dto.StockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class StockClientImpl implements IStockClient {

    private static final Logger logger = LoggerFactory.getLogger(StockClientImpl.class);

    private final WebClient webClient;

    @Autowired
    public StockClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<StockResponse> getStock(String productId, String locationCode) {
        String uri = String.format("/%s/%s", productId, locationCode);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .doOnError(error -> logger.error("Error occurred while calling STOCK service", error));
    }

}
