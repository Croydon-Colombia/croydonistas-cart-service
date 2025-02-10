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
import com.croydon.model.dao.QuoteIncentiveItemsDao;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.QuoteIncentiveItems;
import com.croydon.model.entity.Quotes;
import com.croydon.security.JwtAuthenticationConverter;
import com.croydon.service.IAddOrUpdateQuoteIncentiveItem;
import com.croydon.service.IIncentiveCartManager;
import com.croydon.service.IQuotes;
import com.croydon.utilities.DateUtils;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    QuoteIncentiveItemsDao quoteIncentiveItemsService;
    
    @Autowired
    private JwtAuthenticationConverter jwtAth;

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
    public QuotesDto addOrUpdateIncentiveProduct(ShoppingCartItemDto shoppingCartItemRequest,Jwt jwt) throws IncentiveProductException, ProductException {

        return addOrUpdateQuoteIncentiveItemService.addOrUpdateCartIncentiveItem(shoppingCartItemRequest, jwt);

    }

    /**
     * Elimina un producto de incentivo de un carrito de compras.
     *
     * @param shoppingCartItemRequest el DTO del producto de incentivo a
     * eliminar.
     * @return el DTO del carrito de compras actualizado.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuotesDto deleteIncentiveProduct(ShoppingCartItemDto shoppingCartItemRequest,Jwt jwt) {
        
            Date currentDateTime = DateUtils.getCurrentDate();

    // Buscar la entidad Quotes por su ID
    Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);
    
    jwtAth.validateCustomerAccess(jwt, dbQuotes.getCustomersId().getId());
     
    QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);
    
    // Filtrar y encontrar el incentivo que se va a eliminar
    QuoteIncentiveItems incentiveToRemove = dbQuotes.getQuoteIncentiveItemsCollection()
            .stream()
            .filter(item -> item.getQuoteIncentiveItemsPK().getSku().equals(shoppingCartItemRequest.getProductSku()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Producto sku: " + shoppingCartItemRequest.getProductSku() + ", no encontrado en este pedido."));
    
    // Eliminar el incentivo de la colección en el DTO
    quotesDto.getQuoteIncentiveItemsCollection()
            .removeIf(item -> item.getQuoteIncentiveItemsPK().getSku().equals(shoppingCartItemRequest.getProductSku()));

    // Actualizar la fecha de actualización
    quotesDto.setUpdatedAt(currentDateTime);

    // Convertir el DTO de vuelta a la entidad
    Quotes quotesToUpdate = quotesMapper.quotesDtoToQuotes(quotesDto);

    // Actualizar la cabecera con los totales (si es necesario)
   // updateQuoteHeaderWithTotals(quotesToUpdate);

    // Eliminar el incentivo específico
    quoteIncentiveItemsService.delete(incentiveToRemove);

    // Guardar la entidad Quotes actualizada
    quotesService.save(quotesToUpdate);

    return quotesDto;


    }

}
