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

import com.croydon.exceptions.ShippingAddressException;
import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.service.TotalCalculatorStrategy;
import org.springframework.stereotype.Service;

/**
 * Estrategia de cálculo para calcular el monto del pedido excluyendo impuestos
 * en un carrito de compras.
 *
 * Esta clase extiende TotalCalculatorStrategy y proporciona métodos específicos
 * para calcular el monto del pedido excluyendo impuestos en un carrito de
 * compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class OrderAmountExcludingTaxesCalculatorImpl extends TotalCalculatorStrategy {

    @Override
    public String label() {
        return "Subtotal sin IVA";
    }

    @Override
    public String code() {
        return "subtotal_withouttax";
    }

    @Override
    public int position() {
        return 10;
    }

    /**
     * Calcula el monto del pedido excluyendo impuestos en un carrito de
     * compras.
     *
     * @param quote el DTO del carrito de compras.
     * @param quoteTotal el DTO del total del carrito de compras.
     * @return el DTO del total del carrito de compras actualizado con el
     * subtotal sin impuestos.
     * @throws ShippingAddressException si hay un problema con la dirección de
     * envío.
     */
    @Override
    public QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal) throws ShippingAddressException {

        double total = quote.getQuoteItemsCollection().stream()
                .mapToDouble(QuoteItemsDto::getTotalBasePrice)
                .sum();

        double subtotal = quote.getQuoteItemsCollection().stream()
                .mapToDouble(QuoteItemsDto::getTotalOriginalBasePrice)
                .sum();

        quote.setTotalBase(total);
        quote.setSubtotal(subtotal);
        quoteTotal.setValue(total);

        return quoteTotal;

    }

}
