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
import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.service.TotalCalculatorStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.croydon.service.ICollectsQuoteTotals;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class CollectsQuoteTotalsCalculatorImpl implements ICollectsQuoteTotals {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public QuotesDto quotesDto(QuotesDto quote) throws ShippingAddressException {

        List<TotalCalculatorStrategy> totals
                = new ArrayList<>(this.applicationContext.getBeansOfType(TotalCalculatorStrategy.class).values());

        totals.sort(Comparator.comparingInt(TotalCalculatorStrategy::position));

        List<QuoteTotalsDto> quoteTotalList = new ArrayList<>();

        totals.forEach(
                action -> {
                    try {
                        QuoteTotalsDto result = action.calculateTotal(quote);
                        result.setPosition(action.position());
                        QuoteTotalsDto collectedTotal = action.calculateTotal(quote, result);
                        quoteTotalList.add(collectedTotal);
                    } catch (ShippingAddressException e) {
                        int lel = 19;
                    }
                });

        quote.setQuoteTotalsCollection(quoteTotalList);

        return quote;

    }

}
