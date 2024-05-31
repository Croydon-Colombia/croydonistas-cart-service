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
 * Implementación del servicio para el cálculo del subtotal inclusivo de
 * impuestos en una orden. Implementa la interfaz TotalCalculatorStrategy.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class TaxInclusiveOrderCalculatorImpl extends TotalCalculatorStrategy {

    @Override
    public String label() {
        return "Subtotal";
    }

    @Override
    public String code() {
        return "subtotal_incl_tax";
    }

    @Override
    public int position() {
        return 20;
    }

    /**
     * Calcula el subtotal inclusivo de impuestos para una orden.
     *
     * @param quote El objeto QuotesDto que representa la orden.
     * @param quoteTotal El objeto QuoteTotalsDto que contiene los totales de la
     * orden.
     * @return El objeto QuoteTotalsDto actualizado con el subtotal inclusivo de
     * impuestos.
     * @throws ShippingAddressException Si hay un problema con la dirección de
     * envío.
     */
    @Override
    public QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal) throws ShippingAddressException {
        double total = quote.getQuoteItemsCollection().stream().mapToDouble(QuoteItemsDto::getTotalInclTax).sum();
        quote.setTotalInclTax(total);
        quoteTotal.setValue(total);
        return quoteTotal;
    }

}
