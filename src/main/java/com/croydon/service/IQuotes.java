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
package com.croydon.service;

import com.croydon.model.entity.Customers;
import com.croydon.model.entity.Quotes;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

public interface IQuotes {
    
    Quotes save(Quotes quotes);
    
    Quotes findByCustomersId (Customers id);
    
    Quotes findByQuotesId (Long id);
    
    void delete(Quotes quotes);
    
}
