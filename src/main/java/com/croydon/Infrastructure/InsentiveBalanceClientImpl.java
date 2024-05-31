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

import com.croydon.Infrastructure.dto.IncentiveBalanceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Cliente para obtener el saldo de incentivos de un cliente.
 * 
 * Esta clase implementa la interfaz IInsentiveBalanceClient y proporciona métodos para
 * recuperar el saldo de incentivos de un cliente utilizando un servicio web externo.
 * 
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class InsentiveBalanceClientImpl implements IInsentiveBalanceClient {

    private static final Logger logger = LoggerFactory.getLogger(InsentiveBalanceClientImpl.class);

    @Autowired
    private final WebClient webClient;

    /**
     * Constructor de la clase InsentiveBalanceClientImpl.
     *
     * @param webClient el WebClient utilizado para realizar solicitudes HTTP.
     */
    @Autowired
    public InsentiveBalanceClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Obtiene el saldo de incentivos de un cliente.
     *
     * @param an8 el número de cliente.
     * @return un Mono que emite la respuesta del saldo de incentivos.
     */
    @Override
    public Mono<IncentiveBalanceResponse> getBalance(String an8) {
        String uri = String.format("jdeservices/incentive_available/%s", an8);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(IncentiveBalanceResponse.class)
                .doOnError(error -> logger.error("Error occurred while calling IncentiveBalance service", error.getMessage()));
    }

}
