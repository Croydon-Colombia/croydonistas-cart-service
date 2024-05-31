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

import com.croydon.model.entity.Customers;
import com.croydon.model.entity.Quotes;
import com.croydon.service.INewQuotes;
import com.croydon.service.IPurchaseOrderIncrementId;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para crear nuevos carritos de compras.
 *
 * Esta clase implementa la interfaz INewQuotes y proporciona métodos para crear
 * nuevos carritos de compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class NewQuotesImpl implements INewQuotes {

    @Autowired
    private IPurchaseOrderIncrementId purchaseOrderIncrementIdService;
    @Autowired
    private CustomersImpl CustomersService;

    /**
     * Crea un nuevo carrito de compras para un cliente.
     *
     * @param customerId el ID del cliente para el que se va a crear el carrito
     * de compras.
     * @return el carrito de compras recién creado.
     */
    @Override
    public Quotes makeNewQuotes(String customerId) {

        Quotes quote = new Quotes();
        Date currentDate = DateUtils.getCurrentDate();

        String purchaseOrderIncrement = purchaseOrderIncrementIdService.getNextPurchaseOrderId();

        quote.setIncrementId(purchaseOrderIncrement);

        Customers customer = CustomersService.findById(customerId).get();

        quote.setDiscountAmount(customer.getDiscount());
        quote.setCustomersId(customer);
        quote.setAvailable(true);
        quote.setCreatedAt(currentDate);
        quote.setUpdatedAt(currentDate);
        quote.setLineNumber(1);

        return quote;
    }

}
