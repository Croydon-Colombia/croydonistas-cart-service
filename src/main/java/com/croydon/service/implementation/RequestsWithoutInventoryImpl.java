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

import com.croydon.model.dao.RequestsWithoutInventoryDao;
import com.croydon.model.entity.RequestsWithoutInventory;
import com.croydon.service.IRequestsWithoutInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class RequestsWithoutInventoryImpl implements IRequestsWithoutInventory {

    @Autowired
    private RequestsWithoutInventoryDao service;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RequestsWithoutInventory save(RequestsWithoutInventory requestsWithoutInventory) {
        return service.save(requestsWithoutInventory);
    }

}
