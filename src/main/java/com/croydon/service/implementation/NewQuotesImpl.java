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
import com.croydon.service.IPurchaseOrderIncrementId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class NewQuotesImpl implements INewQuotes {
    
    @Autowired
    private IPurchaseOrderIncrementId purchaseOrderIncrementIdService;

    @Override
    public Quotes makeNewQuotes(String customerId) {
        
        Quotes quote = new Quotes();
        
        String purchaseOrderIncrement = purchaseOrderIncrementIdService.getNextPurchaseOrderId();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        
        quote.setIncrementId(purchaseOrderIncrement);
        quote.setCustomersId(customerId);
        quote.setAvailable(true);
        quote.setCreatedAt(date);
        quote.setUpdatedAt(date);
        return quote;
    }
    
}