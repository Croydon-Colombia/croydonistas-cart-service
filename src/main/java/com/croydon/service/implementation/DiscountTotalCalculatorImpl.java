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

import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.service.TotalCalculatorStrategy;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * Estrategia de cálculo para calcular el total del descuento en un carrito de
 * compras.
 *
 * Esta clase extiende TotalCalculatorStrategy y proporciona métodos específicos
 * para calcular el total del descuento en un carrito de compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class DiscountTotalCalculatorImpl extends TotalCalculatorStrategy {

    @Override
    public String label() {
        return "Valor descuento";
    }

    @Override
    public String code() {
        return "discount_amount";
    }

    @Override
    public int position() {
        return 50;
    }

    /**
     * Calcula el total del descuento en un carrito de compras.
     *
     * @param quote el DTO del carrito de compras.
     * @param quoteTotal el DTO del total del carrito de compras.
     * @return el DTO del total del carrito de compras actualizado con el valor
     * del descuento.
     */
    @Override
    public QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal) {

        Date currentDate = DateUtils.getCurrentDate();

        double discount = quote.getQuoteItemsCollection().stream().mapToDouble(QuoteItemsDto::getTotalDiscount).sum();
        quote.setDiscountAmount(-discount);
        quoteTotal.setValue(discount);
        quoteTotal.setUpdatedAt(currentDate);
        return quoteTotal;
    }

}
