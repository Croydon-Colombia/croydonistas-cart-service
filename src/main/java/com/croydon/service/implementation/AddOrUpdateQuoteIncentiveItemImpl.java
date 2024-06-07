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

import com.croydon.Infrastructure.service.IInsentiveBalanceClient;
import com.croydon.Infrastructure.dto.IncentiveBalanceResponse;
import com.croydon.exceptions.IncentiveProductException;
import com.croydon.exceptions.ProductException;
import com.croydon.mappers.ProductsToQuotesItemsIncentiveMapper;
import com.croydon.mappers.QuoteIncentiveItemsMapper;
import com.croydon.mappers.QuotesMapper;
import com.croydon.model.dto.CustomersDto;
import com.croydon.model.dto.QuoteIncentiveItemsDto;
import com.croydon.model.dto.QuoteIncentiveItemsPKDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Products;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IAddOrUpdateQuoteIncentiveItem;
import com.croydon.service.IIncentiveOperations;
import com.croydon.service.IProducts;
import com.croydon.service.IQuoteIncentiveItems;
import com.croydon.service.IQuotes;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para agregar o actualizar elementos de incentivos
 * en un carrito de compras. Implementa la interfaz
 * {@link IAddOrUpdateQuoteIncentiveItem}.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class AddOrUpdateQuoteIncentiveItemImpl implements IAddOrUpdateQuoteIncentiveItem {

    @Autowired
    private IInsentiveBalanceClient insentiveBalanceClient;

    @Autowired
    private IQuotes quotesService;

    @Autowired
    private QuotesMapper quotesMapper;

    @Autowired
    private IProducts productsComponent;

    @Autowired
    private IIncentiveOperations incentiveOperationsService;

    @Autowired
    private ProductsToQuotesItemsIncentiveMapper productsToQuotesItemsIncentiveMapper;

    @Autowired
    private IQuoteIncentiveItems quoteIncentiveItemsService;

    @Autowired
    private QuoteIncentiveItemsMapper quoteIncentiveItemsMapper;

    /**
     * Agrega o actualiza un producto de incentivo en el carrito de compras.
     *
     * @param shoppingCartItemRequest el producto de incentivo que se desea
     * agregar o actualizar en el carrito.
     * @return el DTO de la cotización actualizada.
     * @throws IncentiveProductException si hay un problema con el producto de
     * incentivo.
     * @throws ProductException si hay un problema con la disponibilidad del
     * producto.
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public QuotesDto addOrUpdateCartIncentiveItem(ShoppingCartItemDto shoppingCartItemRequest) throws IncentiveProductException, ProductException {

        Date currentDate = DateUtils.getCurrentDate();

        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);
        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);
        IncentiveBalanceResponse incentiveBalance = validateIncentiveBalance(quotesDto.getCustomersId());

        if (incentiveBalance.getIncentivoDisponible() == null && !incentiveBalance.getMessage().isEmpty()) {
            throw new IncentiveProductException("Error: " + incentiveBalance.getMessage());
        } else if (incentiveBalance.getIncentivoDisponible() == 0) {
            throw new IncentiveProductException("Tu cupo de incentivos es: 0.0");
        }

        Products dbProduct = productsComponent.findIncetiveBySku(shoppingCartItemRequest.productSku);
        QuoteIncentiveItemsDto quoteIncentiveItemsDto = productsToQuotesItemsIncentiveMapper.ProductsToQuoteIncentiveItemsDto(dbProduct);

        incentiveOperationsService.isIncentiveSumValid(quotesDto, dbProduct, shoppingCartItemRequest, incentiveBalance.getIncentivoDisponible());

        if (existingItem(quotesDto, quoteIncentiveItemsDto)) {
            return updateQuoteWithExistingIncentiveItem(quotesDto, quoteIncentiveItemsDto, currentDate, shoppingCartItemRequest);
        } else {
            return updateQuoteWithNewIncentiveItem(dbProduct, quotesDto, quoteIncentiveItemsDto, currentDate, shoppingCartItemRequest);
        }

    }

    /**
     * Actualiza el carrito de compras con un producto de incentivo existente.
     *
     * @param quotesDto el DTO de la cotización.
     * @param quoteIncentiveItemsDto el DTO del producto de incentivo.
     * @param currentDate la fecha y hora actuales.
     * @param shoppingCartItemRequest el producto de incentivo que se desea
     * actualizar.
     * @return el DTO de la cotización actualizada.
     */
    @Transactional(rollbackFor = Exception.class)
    private QuotesDto updateQuoteWithExistingIncentiveItem(QuotesDto quotesDto, QuoteIncentiveItemsDto quoteIncentiveItemsDto, Date currentDate, ShoppingCartItemDto shoppingCartItemRequest) {

        quotesDto.setUpdatedAt(currentDate);

        QuoteIncentiveItemsDto existingItemExist = quotesDto.getQuoteIncentiveItemsCollection().stream()
                .filter(item -> item.getQuoteIncentiveItemsPK()
                .equals(quoteIncentiveItemsDto.getQuoteIncentiveItemsPK()))
                .findFirst()
                .get();

        quotesDto.getQuoteIncentiveItemsCollection()
                .removeIf(item -> item.getQuoteIncentiveItemsPK()
                .equals(existingItemExist.getQuoteIncentiveItemsPK()));

        existingItemExist.setUpdatedAt(currentDate);
        existingItemExist.setQty(shoppingCartItemRequest.getQuantity());
        existingItemExist.setTotal(existingItemExist.getIncentives() * shoppingCartItemRequest.getQuantity());

        quotesDto.getQuoteIncentiveItemsCollection().add(existingItemExist);

        quoteIncentiveItemsService.save(quoteIncentiveItemsMapper.quoteIncentiveItemsDtoToQuoteIncentiveItems(existingItemExist));

        Quotes quotesToUpdate = quotesMapper.quotesDtoToQuotes(quotesDto);

        updateQuoteHeaderWithTotals(quotesToUpdate);

        return quotesDto;
    }

    /**
     * Agrega un nuevo producto de incentivo al carrito de compras.
     *
     * @param dbProduct el producto de incentivo a agregar.
     * @param quotesDto el DTO de la cotización.
     * @param quoteIncentiveItemsDto el DTO del producto de incentivo.
     * @param currentDate la fecha y hora actuales.
     * @param shoppingCartItemRequest el producto de incentivo que se desea
     * agregar.
     * @return el DTO de la cotización actualizada.
     */
    @Transactional(rollbackFor = Exception.class)
    private QuotesDto updateQuoteWithNewIncentiveItem(Products dbProduct, QuotesDto quotesDto, QuoteIncentiveItemsDto quoteIncentiveItemsDto, Date currentDate, ShoppingCartItemDto shoppingCartItemRequest) {

        QuoteIncentiveItemsPKDto quoteIncentiveItemsPKDto = new QuoteIncentiveItemsPKDto();
        quoteIncentiveItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteIncentiveItemsPKDto.setSku(shoppingCartItemRequest.productSku);
        quoteIncentiveItemsPKDto.setQuotesId(shoppingCartItemRequest.quotes_id);

        double pointsRequest = dbProduct.getLevelIncentive() * shoppingCartItemRequest.getQuantity();

        quoteIncentiveItemsDto.setQuoteIncentiveItemsPK(quoteIncentiveItemsPKDto);
        quoteIncentiveItemsDto.setIncentives(dbProduct.getLevelIncentive());
        quoteIncentiveItemsDto.setLineNumber(quotesDto.getLineNumber());
        quoteIncentiveItemsDto.setTotal(pointsRequest);
        quoteIncentiveItemsDto.setQty(shoppingCartItemRequest.getQuantity());
        quoteIncentiveItemsDto.setCreatedAt(currentDate);
        quoteIncentiveItemsDto.setUpdatedAt(currentDate);
        quoteIncentiveItemsDto.setAdded(true);

        quotesDto.setLineNumber(quotesDto.getLineNumber() + 1);
        quotesDto.getQuoteIncentiveItemsCollection().add(quoteIncentiveItemsDto);
        quotesDto.setHasIncentives(true);
        
        quoteIncentiveItemsService.save(quoteIncentiveItemsMapper.quoteIncentiveItemsDtoToQuoteIncentiveItems(quoteIncentiveItemsDto));

        return quotesDto;
    }

    /**
     * Verifica si un producto de incentivo ya existe en el carrito de compras.
     *
     * @param quotesDto el DTO de la cotización.
     * @param quoteItemsDto el DTO del producto de incentivo.
     * @return true si el producto ya existe en el carrito, false de lo
     * contrario.
     */
    private boolean existingItem(QuotesDto quotesDto, QuoteIncentiveItemsDto quoteItemsDto) {
        Optional<QuoteIncentiveItemsDto> existingItemOptional = quotesDto.getQuoteIncentiveItemsCollection().stream()
                .filter(item -> item.getQuoteIncentiveItemsPK().equals(quoteItemsDto.getQuoteIncentiveItemsPK()))
                .findFirst();
        return existingItemOptional.isPresent();
    }

    /**
     * Valida el saldo de incentivos de un cliente.
     *
     * @param customer el DTO del cliente.
     * @return la respuesta del saldo de incentivos.
     */
    private IncentiveBalanceResponse validateIncentiveBalance(CustomersDto customer) {

        return insentiveBalanceClient.getBalance(customer.getId()).block();

    }

    private void updateQuoteHeaderWithTotals(Quotes quotesDto) {
        Quotes quoteUpdate = quotesDto;
        quoteUpdate.setQuoteIncentiveItemsCollection(null);
        quoteUpdate.setQuoteItemsCollection(null);
        quoteUpdate.setQuoteTotalsCollection(null);
        quotesService.save(quoteUpdate);
    }
}
