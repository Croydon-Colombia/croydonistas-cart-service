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

import com.croydon.model.entity.SaleIncrementsId;
import com.croydon.service.IPurchaseOrderIncrementId;
import com.croydon.service.ISaleIncrementsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class PurchaseOrderIncrementIdImpl implements IPurchaseOrderIncrementId{

    @Autowired
    private ISaleIncrementsId saleIncrementsIdService;
    
    private static final String PURCHASE_ORDER_PREFIX = "44";
    private static final int PURCHASE_ORDER_LENGTH = 10;
    
    @Override
    public String getNextPurchaseOrderId() {
        SaleIncrementsId saleIncrementsId = saleIncrementsIdService.save(new SaleIncrementsId());
        long id = Long.valueOf(padLeftZeros(PURCHASE_ORDER_LENGTH)) + saleIncrementsId.getId();
        return Long.toString(id);
    }
    
    protected String padLeftZeros(int length) {
        return String.format("%s%0" + (length - PURCHASE_ORDER_PREFIX.length()) + "d", PURCHASE_ORDER_PREFIX, 0);
    }
    
}
