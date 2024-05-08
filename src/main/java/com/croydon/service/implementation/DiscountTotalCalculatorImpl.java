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
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class DiscountTotalCalculatorImpl extends TotalCalculatorStrategy{

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

    @Override
    public QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal, List<QuoteItemsDto> quoteItemList) {
        
        Date currentDate = DateUtils.getCurrentDate();
        
        double discount = quoteItemList.stream().mapToDouble(QuoteItemsDto::getTotalDiscount).sum();
        quote.setDiscountAmount(-discount);
        quoteTotal.setValue(discount);
        quoteTotal.setUpdatedAt(currentDate);
        return quoteTotal;
    }
    
}
