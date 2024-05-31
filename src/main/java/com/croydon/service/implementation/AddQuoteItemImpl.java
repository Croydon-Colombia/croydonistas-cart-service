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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para agregar un nuevo ítem a un carrito de compras.
 *
 * Esta clase implementa la interfaz IAddQuoteItem y proporciona métodos para
 * agregar un nuevo ítem a un carrito de compras, calculando impuestos y
 * totales.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class AddQuoteItemImpl implements IAddQuoteItem {

    @Autowired
    private IQuoteItemsTaxesCalculator quoteItemsTaxesCalculator;

    
    /**
     * Agrega un nuevo ítem a un carrito de compras.
     *
     * @param quotesDto el DTO de la cotización.
     * @param quoteItemsDto el DTO del ítem de cotización a agregar.
     * @param product el producto relacionado con el ítem de carrito de compras.
     * @return el DTO del carrito de compras actualizado con el nuevo ítem.
     */
    @Override
    public QuotesDto addNewQuoteItem(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, Products product) {

        QuoteItemsPKDto quoteItemsPKDto = new QuoteItemsPKDto();
        quoteItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteItemsPKDto.setQuotesId(quotesDto.getId());
        quoteItemsPKDto.setSku(quoteItemsDto.getQuoteItemsPK().getSku());

        quoteItemsDto.setAdded(true);
        quoteItemsDto.setTaxCode(quoteItemsDto.getTaxPercent() + "%");
        quoteItemsDto.setQuoteItemsPK(quoteItemsPKDto);

        Optional<AddressesDto> shippingAddressOptional = quotesDto.getCustomersId().getAddressesCollection()
                .stream()
                .filter(address -> address.getShipping().equals(true))
                .findFirst();

        QuoteItemsDto quoteWithTaxes
                = quoteItemsTaxesCalculator.calculateItemTotals(quotesDto, quoteItemsDto, shippingAddressOptional.get(), product);

        quotesDto.getQuoteItemsCollection().add(quoteWithTaxes);

        return quotesDto;
    }
}
