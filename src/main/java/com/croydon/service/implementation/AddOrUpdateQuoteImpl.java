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

import com.croydon.Infrastructure.IStockClient;
import com.croydon.Infrastructure.dto.StockResponse;
import com.croydon.exceptions.ProductException;
import com.croydon.exceptions.ShippingAddressException;
import com.croydon.mappers.ProductsToQuotesItemsMapper;
import com.croydon.mappers.QuotesMapper;
import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuoteItemsPKDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Products;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IAddOrUpdateQuote;
import com.croydon.service.IAddQuoteItem;
import com.croydon.service.ICollectsQuoteTotals;
import com.croydon.service.IProducts;
import com.croydon.service.IQuotes;
import com.croydon.utilities.DateUtils;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para agregar o actualizar productos en el carrito de compras.
 * Implementa la interfaz {@link IAddOrUpdateQuote}.
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class AddOrUpdateQuoteImpl implements IAddOrUpdateQuote {

    @Autowired
    private IStockClient stockClient;

    @Autowired
    private ProductsToQuotesItemsMapper productsToQuotesItemsMapper;

    @Autowired
    private IProducts productsComponent;

    @Autowired
    private ICollectsQuoteTotals collectsQuoteTotalsService;

    @Autowired
    private IAddQuoteItem IAddQuoteItemService;

    @Autowired
    private IQuotes quotesService;

    @Autowired
    private QuotesMapper quotesMapper;

    
    /**
     * Agrega o actualiza un producto en el carrito de compras.
     *
     * @param shoppingCartItemRequest el producto que se desea agregar o actualizar en el carrito.
     * @return el DTO del carrito actualizado.
     * @throws ShippingAddressException si hay un problema con la dirección de envío.
     * @throws ProductException si hay un problema con la disponibilidad del producto.
     */
    @Override
    public QuotesDto addOrUpdateCartProduct(ShoppingCartItemDto shoppingCartItemRequest) throws ShippingAddressException, ProductException {
        
        validateStockAvailability(shoppingCartItemRequest);

        Date currentDateTime = DateUtils.getCurrentDate();

        Products dbProduct = productsComponent.findProductById(shoppingCartItemRequest.productSku);
        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);

        QuoteItemsDto quoteItemsDto = productsToQuotesItemsMapper.ProductsToQuoteItemsDto(dbProduct);
        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);

        quoteItemsDto.setQuoteItemsPK(setQuoteItemsPKDto(quotesDto, dbProduct));

        if (existingItem(quotesDto, quoteItemsDto)) {
            return updateQuoteWithNewItem(quotesDto, quoteItemsDto, dbProduct, currentDateTime, shoppingCartItemRequest.getQuantity());
        } else {
            return addNewItemToQuote(quotesDto, quoteItemsDto, shoppingCartItemRequest.quantity, dbProduct, currentDateTime);
        }
    }

    
    /**
     * Añade un nuevo ítem al carrito de compras.
     *
     * @param quotesDto el DTO del carrito.
     * @param quoteItemsDto el DTO del ítem del carrito.
     * @param quantity la cantidad del producto.
     * @param dbProduct la entidad del producto.
     * @param currentDateTime la fecha y hora actuales.
     * @return el DTO del carrito actualizado con los nuevos totales.
     * @throws ShippingAddressException si hay un problema con la dirección de envío.
     */
    private QuotesDto addNewItemToQuote(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, int quantity, Products dbProduct, Date currentDateTime) throws ShippingAddressException {

        quoteItemsDto.setLineNumber(quotesDto.getLineNumber());
        quoteItemsDto.setQty(quantity);
        quoteItemsDto.setUpdatedAt(currentDateTime);
        quoteItemsDto.setCreatedAt(currentDateTime);

        QuotesDto updatedQuotesDto = IAddQuoteItemService.addNewQuoteItem(quotesDto, quoteItemsDto, dbProduct);
        QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(updatedQuotesDto);

        quotesDto.setLineNumber(quotesDto.getLineNumber() + 1);

        quotesService.save(quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals));
        return quoteDtoWithTotals;

    }

    
    /**
     * Actualiza un ítem existente en el carrito de compras.
     *
     * @param quotesDto el DTO del carrito.
     * @param quoteItemsDto el DTO del ítem del carrito.
     * @param dbProduct la entidad del producto.
     * @param currentDateTime la fecha y hora actuales.
     * @param quantity la cantidad del producto.
     * @return el DTO del carrito actualizado con los nuevos totales.
     * @throws ShippingAddressException si hay un problema con la dirección de envío.
     */ 
    private QuotesDto updateQuoteWithNewItem(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, Products dbProduct, Date currentDateTime, int quantity) throws ShippingAddressException {

        quotesDto.setUpdatedAt(currentDateTime);

        QuoteItemsDto existingItemExist = quotesDto.getQuoteItemsCollection().stream()
                .filter(item -> item.getQuoteItemsPK().equals(quoteItemsDto.getQuoteItemsPK()))
                .findFirst().get();

        existingItemExist.setQty(quantity);
        existingItemExist.setUpdatedAt(currentDateTime);

        quotesDto.getQuoteItemsCollection()
                .removeIf(item -> item.getQuoteItemsPK()
                .equals(quoteItemsDto.getQuoteItemsPK()));

        QuotesDto updatedQuotesDto = IAddQuoteItemService.addNewQuoteItem(quotesDto, existingItemExist, dbProduct);
        QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(updatedQuotesDto);

        quotesService.save(quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals));
        return quoteDtoWithTotals;
    }

    
    /**
     * Comprueba si un ítem ya existe en el carrito de compras.
     *
     * @param quotesDto el DTO del carrito.
     * @param quoteItemsDto el DTO del ítem del carrito.
     * @return true si el ítem ya existe, false en caso contrario.
     */
    private boolean existingItem(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto) {
        Optional<QuoteItemsDto> existingItemOptional = quotesDto.getQuoteItemsCollection().stream()
                .filter(item -> item.getQuoteItemsPK().equals(quoteItemsDto.getQuoteItemsPK()))
                .findFirst();
        return existingItemOptional.isPresent();
    }

    
    /**
     * Valida la disponibilidad de stock para el producto solicitado.
     *
     * @param shoppingCartItemRequest el producto que se desea validar.
     * @throws ProductException si no hay suficiente stock disponible.
     */
    private void validateStockAvailability(ShoppingCartItemDto shoppingCartItemRequest) throws ProductException {
        StockResponse stockResponse = stockClient.getStock(shoppingCartItemRequest.productSku, "CP001").block();
        if (stockResponse.qty < shoppingCartItemRequest.quantity) {
            throw new ProductException(
                    MessageFormat.format("Cantidad solicitada {0}, disponible {1}", shoppingCartItemRequest.quantity, stockResponse.qty)
            );
        }
    }

    
    /**
     * Configura la clave primaria de un ítem del carrito de compras.
     *
     * @param quotesDto el DTO del carrito.
     * @param dbProduct la entidad del producto.
     * @return el DTO de la clave primaria del ítem del carrito.
     */
    private QuoteItemsPKDto setQuoteItemsPKDto(QuotesDto quotesDto, Products dbProduct) {
        QuoteItemsPKDto quoteItemsPKDto = new QuoteItemsPKDto();

        quoteItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteItemsPKDto.setQuotesId(quotesDto.getId());
        quoteItemsPKDto.setSku(dbProduct.getId());

        return quoteItemsPKDto;
    }

}
