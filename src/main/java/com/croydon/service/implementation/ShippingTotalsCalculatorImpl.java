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
import com.croydon.model.dto.AddressesDto;
import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.service.TotalCalculatorStrategy;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
@Service */
@Service
public class ShippingTotalsCalculatorImpl extends TotalCalculatorStrategy {

    @Override
    public String label() {
        return "Costos de envío";
    }

    @Override 
    public String code() {
        return "shipping_amount";
    }

    @Override
    public int position() {
        return 60;
    }

    @Override
    public QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal) throws ShippingAddressException {

        Optional<AddressesDto> shippingAddressOptional = quote.getCustomersId().getAddressesCollection()
                .stream()
                .filter(address -> address.getShipping().equals(true))
                .findFirst();

        AddressesDto shippingAddress = shippingAddressOptional.orElseThrow(()
                -> new ShippingAddressException("No shipping address defined for: AN8-" + quote.getCustomersId().getId()));

        double base = shippingAddress.getCitiesId().getShippingValue();
        double total = base * 1.19;

        quote.setShippingAmountBase(base);
        quote.setShippingInclTax(total);
        quote.setShippingTaxAmount(total - base);
        quote.setShippingValue(total);
        quote.setTaxAmount(quote.getTaxAmount() + quote.getShippingTaxAmount());
        quote.setGrandTotal(quote.getGrandTotal() + total);
        quoteTotal.setValue(total);

        return quoteTotal;
    }

}
