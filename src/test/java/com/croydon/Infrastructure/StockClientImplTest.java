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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@SpringBootTest
public class StockClientImplTest {

    @Autowired
    private IStockClient stockClient;

    /**
     * Test of getStock method, of class StockClientImpl.
     */
    @Test
    public void testGetStock() {
        // Datos de prueba
        String productId = "2000090-40";
        String locationCode = "CP001";
        
        StockResponse stockResponse = new StockResponse();
        stockResponse.setReference(productId);
        stockResponse.setWarehouse(locationCode);
        stockResponse.setQty(1);

        // Verificar el resultado usando StepVerifier
        StepVerifier.create(stockClient.getStock(productId, locationCode))
                .expectNextMatches(response
                        -> response.getReference().equals(stockResponse.getReference())
                && response.getWarehouse().equals(stockResponse.getWarehouse())
                && response.getQty() > 0) // Verificar que qty sea mayor que 0
                .verifyComplete();
    }
}
