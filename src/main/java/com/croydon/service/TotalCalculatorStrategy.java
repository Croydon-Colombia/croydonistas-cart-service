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
package com.croydon.service;

import com.croydon.exceptions.ShippingAddressException;
import com.croydon.mappers.QuoteTotalsMapper;
import com.croydon.mappers.QuoteTotalsPKMapper;
import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.dto.QuoteTotalsPKDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.entity.QuoteTotals;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
public abstract class TotalCalculatorStrategy implements Comparable<TotalCalculatorStrategy>{
    
    @Autowired
    IQuoteTotals quoteTotalsService;
    @Autowired
    QuoteTotalsPKMapper quoteTotalsPKMapper;
    @Autowired
    QuoteTotalsMapper quoteTotalsMapper;
    
    public abstract String label();

    public abstract String code();

    public abstract int position();

    public abstract QuoteTotalsDto calculateTotal(QuotesDto quote, QuoteTotalsDto quoteTotal, List<QuoteItemsDto> quoteItemList) throws ShippingAddressException;

    public QuoteTotalsDto calculateTotal(QuotesDto quote){
        Date currentDate = DateUtils.getCurrentDate();
        
        QuoteTotalsPKDto quoteTotalsPKDto = new QuoteTotalsPKDto();        
        quoteTotalsPKDto.setQuotesId(quote.getId());
        quoteTotalsPKDto.setCode(code());
        
        Optional<QuoteTotals> optional = quoteTotalsService.findByPk(
                quoteTotalsPKMapper.quoteTotalsPKDtoToQuoteTotalsPK(quoteTotalsPKDto));
        if(optional.isPresent())
            return quoteTotalsMapper.quoteTotalsToQuoteTotalsDto(optional.get());
        
        QuoteTotalsDto response = new QuoteTotalsDto();
        response.setQuoteTotalsPK(quoteTotalsPKDto);
        response.setLabel(label());
        response.setPosition(position());
        response.setCreatedAt(currentDate);
        
        return response;
    }
    
    @Override
    public int compareTo(TotalCalculatorStrategy total) {
        return this.position() - total.position();
    }
}
