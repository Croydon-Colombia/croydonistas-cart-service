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
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.entity.ExcludedProducts;
import com.croydon.model.entity.Products;
import com.croydon.service.IExcludedProducts;
import com.croydon.service.IQuoteItemsTaxesCalculator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuoteItemsTaxesCalculatorImpl implements IQuoteItemsTaxesCalculator {

    @Autowired
    private IExcludedProducts excludedProductsService;
    
    @Override
    public QuoteItemsDto calculateItemTotals(QuotesDto quote, QuoteItemsDto quoteItem, AddressesDto shippingAddress, Products products) {

        boolean isExemptCustomer = quote.getCustomersId().getExempt() != null && quote.getCustomersId().getExempt();
        boolean isExemptRegion = shippingAddress.getCitiesId().getRegionsId().getExempt();
        boolean isExcludedRegion = shippingAddress.getCitiesId().getRegionsId().getExcluded();
        boolean isExcludedCity = shippingAddress.getCitiesId().getExcluded();

        if (isExcludedCity || isExcludedRegion) {
            withoutTax(quoteItem,products);
        } else if (isExemptCustomer || isExemptRegion) {
            withoutTax(quoteItem, products);
        } else {
            Optional<ExcludedProducts> excludedItem 
                    = excludedProductsService.findBySku(quoteItem.getQuoteItemsPK().getSku());
            if (excludedItem.isPresent()) {
                withoutTax(quoteItem, products);
            } else {
                calculateTax(quoteItem);
            }
        }

        return updateQuoteItemTotals(quoteItem);
    }

    private QuoteItemsDto withoutTax(QuoteItemsDto quoteItem, Products products) {
        
        double taxPercent = products.getTaxPercent() / 100;
        double discountPercent = products.getCustomerDiscount() / 100;
        
        double discountTotal = products.getPrice() * discountPercent;
        double basePrice = products.getPrice() - discountTotal;
        double basePriceTax = basePrice * taxPercent;
        double basePriceWithTax = basePrice + basePriceTax;
        
        quoteItem.setBasePrice(basePrice);
        quoteItem.setBasePriceInclTax(basePriceWithTax);
        quoteItem.setBasePriceJde(basePrice);
        quoteItem.setDiscountPrice(discountTotal);
        quoteItem.setPriceInclTax(basePriceWithTax);
        quoteItem.setTaxAmount(basePriceTax);
        
        quoteItem.setPercentDiscount(products.getCustomerDiscount());
        
        quoteItem.setTotalBasePrice(basePrice);
        quoteItem.setTotalDiscount(discountTotal);
        quoteItem.setTotalInclTax(basePriceWithTax);
        quoteItem.setTotalOriginalBasePrice(products.getPrice());
        quoteItem.setTotalTaxAmount(basePriceTax);
        quoteItem.setOriginalBasePrice(products.getPrice());
        
        return quoteItem;
    }

    private QuoteItemsDto calculateTax(QuoteItemsDto quoteItem) {
        double taxAmount = quoteItem.getBasePrice() * (quoteItem.getTaxPercent() / 100);
        quoteItem.setTaxAmount(taxAmount);
        quoteItem.setPriceInclTax(quoteItem.getBasePrice() + taxAmount);
        double basePriceInclTax = quoteItem.getBasePrice() * (1 + (quoteItem.getTaxPercent() / 100));
        quoteItem.setBasePriceInclTax(basePriceInclTax);
        return quoteItem;
    }

    private QuoteItemsDto updateQuoteItemTotals(QuoteItemsDto quoteItem) {
        quoteItem.setTotalBasePrice(quoteItem.getBasePrice() * quoteItem.getQty());
        quoteItem.setTotalDiscount(quoteItem.getDiscountPrice() * quoteItem.getQty());
        quoteItem.setTotalInclTax(quoteItem.getPriceInclTax() * quoteItem.getQty());
        quoteItem.setTotalTaxAmount(quoteItem.getTaxAmount() * quoteItem.getQty());
        return quoteItem;
    }
}
