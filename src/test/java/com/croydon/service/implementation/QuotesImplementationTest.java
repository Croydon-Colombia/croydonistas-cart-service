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
package com.croydon.service.implementation;

import com.croydon.model.entity.Quotes;
import com.croydon.service.IQuotes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@SpringBootTest()
public class QuotesImplementationTest {

    @Autowired
    private IQuotes quotesService;

    /**
     * Test the findByCustomersId method in the QuotesImplementation class.
     * Verifies that a valid result is returned for a given customer ID. The
     * expected result is that the returned value should not be null, and the
     * customer ID in the result should match the provided customer ID.
     */
    @Test
    public void testFindByCustomersId() {
        String customerId = "6716";
        System.out.println("findByCustomersId = " + customerId);
        
        
        Quotes result = quotesService.findByCustomersId(customerId);
        
        assertNotNull(result);
        assertEquals(customerId, result.getCustomersId());
    }

}
