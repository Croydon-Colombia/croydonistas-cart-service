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

import com.croydon.model.dto.AddressesDto;
import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuoteItemsPKDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.entity.Products;
import com.croydon.service.IAddQuoteItem;
import com.croydon.service.IQuoteItemsTaxesCalculator;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class AddQuoteItemImpl implements IAddQuoteItem {

    @Autowired
    private IQuoteItemsTaxesCalculator quoteItemsTaxesCalculator;

    @Override
    public QuotesDto addNewQuoteItem(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, Products product) {

        Date currentDate = DateUtils.getCurrentDate();
        QuoteItemsPKDto quoteItemsPKDto = new QuoteItemsPKDto();
        quoteItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteItemsPKDto.setQuotesId(quotesDto.getId());
        quoteItemsPKDto.setSku(quoteItemsDto.getQuoteItemsPK().getSku());

        quoteItemsDto.setLineNumber(quotesDto.getLineNumber());
        quoteItemsDto.setTaxCode(quoteItemsDto.getTaxPercent() + "%");
        quoteItemsDto.setQuoteItemsPK(quoteItemsPKDto);
        quoteItemsDto.setCreatedAt(currentDate);
        quoteItemsDto.setUpdatedAt(currentDate);

        Optional<AddressesDto> shippingAddressOptional = quotesDto.getCustomersId().getAddressesCollection()
                .stream()
                .filter(address -> address.getShipping().equals(true))
                .findFirst(); 
        
        QuoteItemsDto quoteWithTaxes 
                = quoteItemsTaxesCalculator.calculateItemTotals(quotesDto, quoteItemsDto, shippingAddressOptional.get(), product);
        
        quotesDto.getQuoteItemsCollection().add(quoteWithTaxes);

        int lineNumber = quotesDto.getLineNumber();
        lineNumber++;
        quotesDto.setLineNumber(lineNumber);

        return quotesDto;
    }
}
