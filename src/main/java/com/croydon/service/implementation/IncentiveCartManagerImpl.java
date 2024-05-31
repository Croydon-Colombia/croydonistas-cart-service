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

import com.croydon.exceptions.IncentiveProductException;
import com.croydon.exceptions.ProductException;
import com.croydon.mappers.QuotesMapper;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IAddOrUpdateQuoteIncentiveItem;
import com.croydon.service.IIncentiveCartManager;
import com.croydon.service.IQuotes;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Gestor de incentivos para un carrito de compras.
 *
 * Esta clase implementa la interfaz IIncentiveCartManager y proporciona métodos
 * para agregar, actualizar y eliminar productos de incentivo en un carrito de
 * compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class IncentiveCartManagerImpl implements IIncentiveCartManager {

    @Autowired
    private IAddOrUpdateQuoteIncentiveItem addOrUpdateQuoteIncentiveItemService;

    @Autowired
    private IQuotes quotesService;

    @Autowired
    private QuotesMapper quotesMapper;

    /**
     * Agrega o actualiza un producto de incentivo en un carrito de compras.
     *
     * @param shoppingCartItemRequest el DTO del producto de incentivo a agregar
     * o actualizar.
     * @return el DTO del carrito de compras actualizado.
     * @throws IncentiveProductException si hay un problema con el producto de
     * incentivo.
     * @throws ProductException si hay un problema con la disponibilidad del
     * producto.
     */
    @Override
    public QuotesDto addOrUpdateIncentiveProduct(ShoppingCartItemDto shoppingCartItemRequest) throws IncentiveProductException, ProductException {

        return addOrUpdateQuoteIncentiveItemService.addOrUpdateCartIncentiveItem(shoppingCartItemRequest);

    }

    /**
     * Elimina un producto de incentivo de un carrito de compras.
     *
     * @param shoppingCartItemRequest el DTO del producto de incentivo a
     * eliminar.
     * @return el DTO del carrito de compras actualizado.
     */
    @Override
    public QuotesDto deleteIncentiveProduct(ShoppingCartItemDto shoppingCartItemRequest) {
        Date currentDateTime = DateUtils.getCurrentDate();

        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);
        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);

        quotesDto.getQuoteIncentiveItemsCollection()
                .removeIf(item -> item.getQuoteIncentiveItemsPK().getSku()
                .equals(shoppingCartItemRequest.getProductSku()));

        quotesDto.setUpdatedAt(currentDateTime);

        quotesService.save(quotesMapper.quotesDtoToQuotes(quotesDto));

        return quotesDto;

    }

}
