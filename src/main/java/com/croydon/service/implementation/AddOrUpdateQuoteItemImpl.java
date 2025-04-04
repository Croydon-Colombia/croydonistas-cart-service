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

import com.croydon.Infrastructure.service.IStockClient;
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
import com.croydon.model.entity.RequestsWithoutInventory;
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
import com.croydon.service.IAddOrUpdateQuoteItem;
import com.croydon.service.IRequestsWithoutInventory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para agregar o actualizar productos en el carrito de compras.
 * Implementa la interfaz {@link IAddOrUpdateQuoteItem}.
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class AddOrUpdateQuoteItemImpl implements IAddOrUpdateQuoteItem {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AddOrUpdateQuoteItemImpl.class);

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

    @Autowired
    private IRequestsWithoutInventory requestsWithoutInventoryService;

    /**
     * Agrega o actualiza un producto en el carrito de compras.
     *
     * @param shoppingCartItemRequest el producto que se desea agregar o
     * actualizar en el carrito.
     * @return el DTO del carrito actualizado.
     * @throws ShippingAddressException si hay un problema con la dirección de
     * envío.
     * @throws ProductException si hay un problema con la disponibilidad del
     * producto.
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public QuotesDto addOrUpdateCartProduct(ShoppingCartItemDto shoppingCartItemRequest) throws ShippingAddressException, ProductException {

        if (shoppingCartItemRequest.quantity < 1) {
            throw new ProductException("Ingresa una cantidad válida para añadir producto");
        }

        Date currentDateTime = DateUtils.getCurrentDate();
        List<Products> dbProducts = productsComponent.findProductById(shoppingCartItemRequest.productSku);
        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);

        // Obtener el stock disponible de todos los productos en una sola iteración
        Map<String, Integer> stockMap = new HashMap<>();
        int totalAvailableStock = 0;
        for (Products product : dbProducts) {
            int stock = stockClient.getStock(product.getId(), "CP001").block().getQty();
            stockMap.put(product.getId(), stock);
            totalAvailableStock += stock;
            if (totalAvailableStock >= shoppingCartItemRequest.quantity) {
                break; // Detener si ya tenemos suficiente stock
            }
        }

        if (totalAvailableStock < shoppingCartItemRequest.quantity) {
            throw new ProductException(MessageFormat.format("Cantidad solicitada {0}, disponible {1}", shoppingCartItemRequest.quantity, totalAvailableStock));
        }

        int remainingQuantity = shoppingCartItemRequest.quantity;
        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);

        for (Products dbProduct : dbProducts) {
            int availableStock = stockMap.getOrDefault(dbProduct.getId(), 0);

            if (availableStock > 0) {
                int quantityToAdd = Math.min(availableStock, remainingQuantity);
                remainingQuantity -= quantityToAdd;

                QuoteItemsDto quoteItemsDto = productsToQuotesItemsMapper.ProductsToQuoteItemsDto(dbProduct);

                String substituteCode = dbProduct.getSubstituteCode();
                quoteItemsDto.setSubstituteCode((substituteCode == null || substituteCode.isEmpty()) ? dbProduct.getId() : substituteCode);

                if (substituteCode != null && !substituteCode.isEmpty()) {
                    quoteItemsDto.setName(dbProduct.getSubstituteDescription());
                }

                quoteItemsDto.setQuoteItemsPK(setQuoteItemsPKDto(quotesDto, dbProduct));

                if (existingItem(quotesDto, quoteItemsDto)) {
                    quotesDto = updateQuoteWithExististItem(quotesDto, quoteItemsDto, dbProduct, currentDateTime, quantityToAdd, shoppingCartItemRequest.getIsUpdateOnly());
                } else {
                    quotesDto = addNewItemToQuote(quotesDto, quoteItemsDto, quantityToAdd, dbProduct, currentDateTime);
                }

                if (remainingQuantity == 0) {
                    break; // Detener iteración si ya cubrimos la cantidad necesaria
                }
            }
        }

        return quotesDto;
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
     * @throws ShippingAddressException si hay un problema con la dirección de
     * envío.
     */
    @Transactional(rollbackFor = Exception.class)
    private QuotesDto updateQuoteWithExististItem(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, Products dbProduct, Date currentDateTime, int quantity, boolean isUpdateOnly) throws ShippingAddressException, ProductException {

        quotesDto.setUpdatedAt(currentDateTime);

        QuoteItemsDto existingItemExist = quotesDto.getQuoteItemsCollection().stream()
                .filter(item -> item.getQuoteItemsPK().equals(quoteItemsDto.getQuoteItemsPK()))
                .findFirst().get();
        if (isUpdateOnly == true) {
            existingItemExist.setQty(quantity);
        } else {
            existingItemExist.setQty(existingItemExist.getQty() + quantity);

        }
        existingItemExist.setUpdatedAt(currentDateTime);
        quotesDto.getQuoteItemsCollection()
                .removeIf(item -> item.getQuoteItemsPK()
                .equals(quoteItemsDto.getQuoteItemsPK()));

        QuotesDto updatedQuotesDto = IAddQuoteItemService.addNewQuoteItem(quotesDto, existingItemExist, dbProduct);
        QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(updatedQuotesDto);

        Quotes quotesToUpdate = quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals);

        updateQuoteHeaderWithTotals(quotesToUpdate);

        return quoteDtoWithTotals;
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
     * @throws ShippingAddressException si hay un problema con la dirección de
     * envío.
     */
    @Transactional(rollbackFor = Exception.class)
    private QuotesDto addNewItemToQuote(QuotesDto quotesDto, QuoteItemsDto quoteItemsDto, int quantity, Products dbProduct, Date currentDateTime) throws ShippingAddressException, ProductException {

        quoteItemsDto.setLineNumber(quotesDto.getLineNumber());
        quoteItemsDto.setQty(quantity);
        quoteItemsDto.setUpdatedAt(currentDateTime);
        quoteItemsDto.setCreatedAt(currentDateTime);

        QuotesDto updatedQuotesDto = IAddQuoteItemService.addNewQuoteItem(quotesDto, quoteItemsDto, dbProduct);
        QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(updatedQuotesDto);

        quoteDtoWithTotals.setLineNumber(quotesDto.getLineNumber() + 1);

        Quotes quotesToUpdate = quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals);

        updateQuoteHeaderWithTotals(quotesToUpdate);

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
    private void validateStockAvailability(ShoppingCartItemDto shoppingCartItemRequest, Quotes dbQuotes, Products dbProduct) throws ProductException {
        StockResponse stockResponse = stockClient.getStock(dbProduct.getId(), "CP001").block();
        if (stockResponse.getQty() < shoppingCartItemRequest.getQuantity()) {
            saveRequestsWithoutInventory(dbQuotes, shoppingCartItemRequest, stockResponse.qty);
            throw new ProductException(
                    MessageFormat.format("Cantidad solicitada {0}, disponible {1}", shoppingCartItemRequest.quantity, stockResponse.qty)
            );
        }
    }

    private void saveRequestsWithoutInventory(Quotes dbQuotes, ShoppingCartItemDto shoppingCartItemRequest, int stockResponse) {
        try {
            int diferencia = shoppingCartItemRequest.quantity - stockResponse;

            RequestsWithoutInventory requestsWithoutInventory = new RequestsWithoutInventory();

            requestsWithoutInventory.setCustomerId(dbQuotes.getCustomersId().getId());
            requestsWithoutInventory.setSku(shoppingCartItemRequest.productSku);
            requestsWithoutInventory.setCustomerFiscalId(dbQuotes.getCustomersId().getDocumentNumber());
            requestsWithoutInventory.setQtyRequests(shoppingCartItemRequest.quantity);
            requestsWithoutInventory.setQtyAvailable(stockResponse);
            requestsWithoutInventory.setQtyDiference(diferencia);
            requestsWithoutInventory.setProductType("product");
            requestsWithoutInventory.setQuotesId(dbQuotes.id);
            requestsWithoutInventory.setEventType("shopping_cart");
            requestsWithoutInventory.setCreatedAt(new Date()); // Fecha y hora actual
            requestsWithoutInventory.setReported(false);

            requestsWithoutInventoryService.save(requestsWithoutInventory);

        } catch (Exception e) {
            logger.error("Error at RequestsWithoutInventory: " + e.getMessage());
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

    private void updateQuoteHeaderWithTotals(Quotes quotesDto) {
        Quotes quoteUpdate = quotesDto;
        quoteUpdate.setQuoteIncentiveItemsCollection(null);
        quoteUpdate.setQuoteItemsCollection(null);
        quoteUpdate.setQuoteTotalsCollection(null);
        quotesService.save(quoteUpdate);
    }
}
