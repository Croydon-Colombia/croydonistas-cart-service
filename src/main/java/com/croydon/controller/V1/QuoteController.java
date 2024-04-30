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
package com.croydon.controller.V1;

import com.croydon.model.entity.Quotes;
import com.croydon.service.IQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

@RestController
@RequestMapping("/shopping-cart/v1")
public class QuoteController {
    
    @Autowired
    private IQuotes quotesService;
    
    /*
    Getters methods
    */
    @GetMapping("quote-by-customerid")
    public Quotes findQuotesByClientId(@RequestParam("customerId") String customerId){
        return quotesService.findByCustomersId(customerId);
    }
    
    
    
    /*
    Post methods
    */
}
