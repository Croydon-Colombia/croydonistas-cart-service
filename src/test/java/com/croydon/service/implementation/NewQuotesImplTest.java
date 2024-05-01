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
import com.croydon.service.INewQuotes;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@SpringBootTest
public class NewQuotesImplTest {
    
    @Autowired
    private INewQuotes newQuotesService;
    
    public NewQuotesImplTest() {
    }

    /**
     * Test of makeNewQuotes method, of class NewQuotesImpl.
     * Check that a new shopping cart is generated for a customer;
     * it is expected that the creation date and time of the shopping cart match the current moment.
     */
    @Test
    public void testMakeNewQuotes() {
        System.out.println("makeNewQuotes");
        String customerId = "9999";
        
        LocalDateTime localDateTime = LocalDateTime.now();
        
        Quotes result = newQuotesService.makeNewQuotes(customerId);
        
        LocalDateTime dateTimeQuoteCreate = result.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();;
        
        assertEquals(localDateTime.getYear(), dateTimeQuoteCreate.getYear());
        assertEquals(localDateTime.getMonth(), dateTimeQuoteCreate.getMonth());
        assertEquals(localDateTime.getDayOfMonth(), dateTimeQuoteCreate.getDayOfMonth());
        assertEquals(localDateTime.getHour(), dateTimeQuoteCreate.getHour());
        assertEquals(localDateTime.getMinute(), dateTimeQuoteCreate.getMinute());
    }
    
}
