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

import com.croydon.exceptions.ProductException;
import com.croydon.exceptions.ShippingAddressException;
import com.croydon.mappers.QuotesMapper;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Customers;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IAddOrUpdateQuote;
import com.croydon.service.ICollectsQuoteTotals;
import com.croydon.service.ICustomers;
import com.croydon.service.INewQuotes;
import com.croydon.service.IQuotes;
import com.croydon.service.IShoppingCartManager;
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
public class ShoppingCartManagerImpl implements IShoppingCartManager {

    @Autowired
    private IQuotes quotesService;

    @Autowired
    private INewQuotes newQuotesService;

    @Autowired
    private QuotesMapper quotesMapper;

    @Autowired
    private ICustomers customersService;

    @Autowired
    private IAddOrUpdateQuote addOrUpdateQuoteService;

    @Autowired
    private ICollectsQuoteTotals collectsQuoteTotalsService;

    @Override
    public QuotesDto getOrCreateCart(String customerId) {

        Optional<Customers> customer = customersService.findById(customerId);
        if (!customer.isPresent()) {
            throw new IllegalArgumentException("Customer not found");
        }

        Quotes quote = quotesService.findByCustomersId(customer.get());
        if (quote == null) {
            quote = newQuotesService.makeNewQuotes(customerId);
        }

        quotesService.save(quote);
        return quotesMapper.quotesToQuotesDto(quote);

    }

    @Override
    public QuotesDto addOrUpdateCartProduct(ShoppingCartItemDto shoppingCartItemRequest) throws ShippingAddressException, ProductException {
        return addOrUpdateQuoteService.addOrUpdateCartProduct(shoppingCartItemRequest);
    }

    @Override
    public QuotesDto deleteCartProduct(ShoppingCartItemDto shoppingCartItemRequest) throws ShippingAddressException {
        Date currentDateTime = DateUtils.getCurrentDate();

        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);
        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);

        quotesDto.getQuoteItemsCollection()
                .removeIf(item -> item.getQuoteItemsPK().getSku()
                .equals(shoppingCartItemRequest.getProductSku()));

        QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(quotesDto);
        quoteDtoWithTotals.setUpdatedAt(currentDateTime);
        
        quotesService.save(quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals));
        
        return quoteDtoWithTotals;
    }
}
