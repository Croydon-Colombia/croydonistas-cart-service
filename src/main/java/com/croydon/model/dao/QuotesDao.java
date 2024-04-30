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
package com.croydon.model.dao;

import com.croydon.model.entity.Quotes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

public interface QuotesDao extends CrudRepository<Quotes, Long> {
    
    @Query("SELECT q FROM Quotes q WHERE q.customersId = :customerId and available = true")
    Quotes findByCustomersId(@Param("customerId") String customerId);
    
}
