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
import com.croydon.Infrastructure.dto.StockResponse;
import com.croydon.Infrastructure.service.IStockClient;
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
import com.croydon.model.entity.RequestsWithoutInventory;
import com.croydon.service.IAddOrUpdateQuoteIncentiveItem;
import com.croydon.service.IIncentiveOperations;
import com.croydon.service.IProducts;
import com.croydon.service.IQuoteIncentiveItems;
import com.croydon.service.IQuotes;
import com.croydon.service.IRequestsWithoutInventory;
import com.croydon.utilities.DateUtils;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;
import org.slf4j.LoggerFactory;
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

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AddOrUpdateQuoteIncentiveItemImpl.class);

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

    @Autowired
    private IStockClient stockClient;

    @Autowired
    private IRequestsWithoutInventory requestsWithoutInventoryService;

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

        Products dbProduct = productsComponent.findIncetiveBySku(shoppingCartItemRequest.productSku);
        
        validateQuantity(shoppingCartItemRequest.getQuantity());

        Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);

        validateStockAvailability(dbQuotes, shoppingCartItemRequest);

        Date currentDate = DateUtils.getCurrentDate();

        QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);
        IncentiveBalanceResponse incentiveBalance = validateIncentiveBalance(quotesDto.getCustomersId());

        validateIncentiveBalanceResponse(incentiveBalance);
        
        QuoteIncentiveItemsDto quoteIncentiveItemsDto = productsToQuotesItemsIncentiveMapper.ProductsToQuoteIncentiveItemsDto(dbProduct);

        setQuoteIncentiveItemsPK(quotesDto, shoppingCartItemRequest, quoteIncentiveItemsDto, dbProduct);

        boolean itemExists = existingItem(quotesDto, quoteIncentiveItemsDto, shoppingCartItemRequest.getIsUpdateOnly());

        validateAndUpdateIncentiveItem(quotesDto, shoppingCartItemRequest, dbProduct, quoteIncentiveItemsDto, itemExists, incentiveBalance);

        return itemExists ? updateQuoteWithExistingIncentiveItem(quotesDto, quoteIncentiveItemsDto, currentDate, shoppingCartItemRequest)
                : updateQuoteWithNewIncentiveItem(dbProduct, quotesDto, quoteIncentiveItemsDto, currentDate, shoppingCartItemRequest);
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

        quoteIncentiveItemsDto.setIncentives(dbProduct.getLevelIncentive());
        quoteIncentiveItemsDto.setLineNumber(quotesDto.getLineNumber());
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
    private boolean existingItem(QuotesDto quotesDto, QuoteIncentiveItemsDto quoteItemsDto, boolean isUpdateOnly) {
        Optional<QuoteIncentiveItemsDto> existingItemOptional = quotesDto.getQuoteIncentiveItemsCollection().stream()
                .filter(item -> item.getQuoteIncentiveItemsPK().equals(quoteItemsDto.getQuoteIncentiveItemsPK()))
                .findFirst();
        if (existingItemOptional.isPresent() && isUpdateOnly == false) {
            return true;
        } else if (existingItemOptional.isPresent() && isUpdateOnly == true) {
            // Actualizar los datos del ítem existente con los del ítem proporcionado
            QuoteIncentiveItemsDto existingItem = existingItemOptional.get();
            updateIncentiveItem(existingItem, quoteItemsDto);
            return true;
        }

        return false;
        // return existingItemOptional.isPresent();
    }

    /**
     * Actualiza las cantidades y totales de un ítem de incentivo existente con
     * los valores de un nuevo ítem de incentivo.
     *
     * @param existingItem El ítem de incentivo existente que se va a
     * actualizar.
     * @param newItem El nuevo ítem de incentivo que proporciona los valores
     * actualizados.
     */
    private void updateIncentiveItem(QuoteIncentiveItemsDto existingItem, QuoteIncentiveItemsDto newItem) {
        existingItem.setQty(newItem.getQty());
        existingItem.setTotal(newItem.getTotal());
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

    /**
     * Valida que la cantidad del producto de incentivo sea correcta.
     *
     * Este método verifica que la cantidad proporcionada sea mayor o igual a 1,
     * lanzando una excepción si la cantidad es menor. Es crucial para asegurar
     * que no se añadan incentivos con cantidades inválidas al carrito de
     * compras.
     *
     * @param quantity La cantidad del producto de incentivo que se desea
     * agregar.
     * @throws IncentiveProductException Si la cantidad es menor que 1.
     */
    private void validateQuantity(int quantity) throws IncentiveProductException {
        if (quantity < 1) {
            throw new IncentiveProductException("Ingresa cantidad válida para añadir incentivo.");
        }
    }

    /**
     * Valida la respuesta del saldo de incentivos.
     *
     * Este método verifica que la respuesta del saldo de incentivos sea válida.
     * Si el incentivo disponible es nulo y hay un mensaje de error, lanza una
     * excepción con el mensaje proporcionado. Si el incentivo disponible es 0,
     * lanza una excepción indicando que el cupo de incentivos es cero.
     *
     * @param incentiveBalance La respuesta del saldo de incentivos a validar.
     * @throws IncentiveProductException Si la respuesta del saldo de incentivos
     * es inválida.
     */
    private void validateIncentiveBalanceResponse(IncentiveBalanceResponse incentiveBalance) throws IncentiveProductException {
        if (incentiveBalance.getIncentivoDisponible() == null && !incentiveBalance.getMessage().isEmpty()) {
            throw new IncentiveProductException("Error: " + incentiveBalance.getMessage());
        } else if (incentiveBalance.getIncentivoDisponible() == 0) {
            throw new IncentiveProductException("Tu cupo de incentivos es: 0.0");
        }
    }

    /**
     * Este método configura la clave primaria compuesta (PK) del objeto
     * QuoteIncentiveItemsDto utilizando la información proporcionada en el
     * objeto QuotesDto y el objeto ShoppingCartItemDto. La clave primaria
     * compuesta está formada por el ID del cliente, el SKU del producto y el ID
     * de la cotización.
     *
     * @param quotesDto El DTO de la cotización que contiene el ID del cliente.
     * @param shoppingCartItemRequest El DTO del producto de incentivo que
     * contiene el SKU y el ID de la cotización.
     * @param quoteIncentiveItemsDto El DTO del item de incentivo donde se
     * establecerá la clave primaria compuesta.
     *
     */
    private void setQuoteIncentiveItemsPK(QuotesDto quotesDto, ShoppingCartItemDto shoppingCartItemRequest, QuoteIncentiveItemsDto quoteIncentiveItemsDto, Products dbProduct) {

        QuoteIncentiveItemsPKDto quoteIncentiveItemsPKDto = new QuoteIncentiveItemsPKDto();
        double pointsRequest = dbProduct.getLevelIncentive() * shoppingCartItemRequest.getQuantity();
        quoteIncentiveItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteIncentiveItemsPKDto.setSku(shoppingCartItemRequest.productSku);
        quoteIncentiveItemsPKDto.setQuotesId(shoppingCartItemRequest.quotes_id);
        quoteIncentiveItemsDto.setQty(shoppingCartItemRequest.getQuantity());
        quoteIncentiveItemsDto.setTotal(pointsRequest);
        quoteIncentiveItemsDto.setQuoteIncentiveItemsPK(quoteIncentiveItemsPKDto);
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
    private QuotesDto updateQuoteWithExistingIncentiveItem(
            QuotesDto quotesDto, QuoteIncentiveItemsDto quoteIncentiveItemsDto,
            Date currentDate, ShoppingCartItemDto shoppingCartItemRequest) {

        quotesDto.setUpdatedAt(currentDate);

        QuoteIncentiveItemsDto existingItemExist = quotesDto.getQuoteIncentiveItemsCollection().stream()
                .filter(item -> item.getQuoteIncentiveItemsPK()
                .equals(quoteIncentiveItemsDto.getQuoteIncentiveItemsPK()))
                .findFirst()
                .get();
        if (shoppingCartItemRequest.getIsUpdateOnly() == true) {
            existingItemExist.setQty(shoppingCartItemRequest.getQuantity());
            existingItemExist.setTotal(existingItemExist.getIncentives() * shoppingCartItemRequest.getQuantity());
        } else {
            existingItemExist.setQty(existingItemExist.getQty() + shoppingCartItemRequest.getQuantity());
            existingItemExist.setTotal(existingItemExist.getIncentives() * existingItemExist.getQty());
        }
        quotesDto.getQuoteIncentiveItemsCollection()
                .removeIf(item -> item.getQuoteIncentiveItemsPK()
                .equals(existingItemExist.getQuoteIncentiveItemsPK()));

        existingItemExist.setUpdatedAt(currentDate);

        quotesDto.getQuoteIncentiveItemsCollection().add(existingItemExist);

        quoteIncentiveItemsService.save(quoteIncentiveItemsMapper.quoteIncentiveItemsDtoToQuoteIncentiveItems(existingItemExist));

        Quotes quotesToUpdate = quotesMapper.quotesDtoToQuotes(quotesDto);

        updateQuoteHeaderWithTotals(quotesToUpdate);

        return quotesDto;
    }

    /**
     * Valida y actualiza un item de incentivo en la cotización.
     *
     * Este método verifica si el item de incentivo ya existe en la cotización.
     * Si el item existe, actualiza temporalmente su cantidad y total para
     * realizar la validación del incentivo, luego restaura los valores
     * originales. Si el item no existe, realiza la validación del incentivo con
     * los nuevos datos.
     *
     * @param quotesDto El DTO de la cotización.
     * @param shoppingCartItemRequest El DTO del producto de incentivo
     * solicitado.
     * @param dbProduct El producto de incentivo obtenido de la base de datos.
     * @param quoteIncentiveItemsDto El DTO del item de incentivo.
     * @param itemExists Booleano que indica si el item ya existe en la
     * cotización.
     * @param incentiveBalance La respuesta del saldo de incentivos del cliente.
     * @throws ProductException Si el producto de incentivo no se encuentra en
     * la cotización.
     * @throws IncentiveProductException Si la suma de incentivos no es válida.
     */
    private void validateAndUpdateIncentiveItem(QuotesDto quotesDto, ShoppingCartItemDto shoppingCartItemRequest, Products dbProduct, QuoteIncentiveItemsDto quoteIncentiveItemsDto, boolean itemExists, IncentiveBalanceResponse incentiveBalance) throws ProductException, IncentiveProductException {
        if (itemExists) {
            QuoteIncentiveItemsDto existingItem = quotesDto.getQuoteIncentiveItemsCollection().stream()
                    .filter(item -> item.getQuoteIncentiveItemsPK().equals(quoteIncentiveItemsDto.getQuoteIncentiveItemsPK()))
                    .findFirst()
                    .orElseThrow(() -> new ProductException("El producto de incentivo no se encontró en la cotización."));

            int originalQty = existingItem.getQty();
            double originalTotal = existingItem.getTotal();
            if (shoppingCartItemRequest.getIsUpdateOnly() == true) {
                existingItem.setQty(shoppingCartItemRequest.getQuantity());
                existingItem.setTotal(existingItem.getIncentives() * shoppingCartItemRequest.getQuantity());
            } else {
                existingItem.setQty(existingItem.getQty() + shoppingCartItemRequest.getQuantity());
                existingItem.setTotal(existingItem.getIncentives() * existingItem.getQty());
            }

            incentiveOperationsService.isIncentiveUpdateSumValid(quotesDto, dbProduct, shoppingCartItemRequest, incentiveBalance.getIncentivoDisponible());

            existingItem.setQty(originalQty);
            existingItem.setTotal(originalTotal);
        } else {
            incentiveOperationsService.isIncentiveSumValid(quotesDto, dbProduct, shoppingCartItemRequest, incentiveBalance.getIncentivoDisponible());
        }
    }

    /**
     * Valida la disponibilidad de stock para el producto solicitado.
     *
     * @param shoppingCartItemRequest el producto que se desea validar.
     * @throws ProductException si no hay suficiente stock disponible.
     */
    private void validateStockAvailability(Quotes dbQuotes, ShoppingCartItemDto shoppingCartItemRequest) throws ProductException {
        StockResponse stockResponse = stockClient.getStock(shoppingCartItemRequest.productSku, "CP001").block();
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
            requestsWithoutInventory.setProductType("incentive");
            requestsWithoutInventory.setQuotesId(dbQuotes.id);
            requestsWithoutInventory.setEventType("shopping_cart");
            requestsWithoutInventory.setCreatedAt(new Date()); // Fecha y hora actual
            requestsWithoutInventory.setReported(false);

            requestsWithoutInventoryService.save(requestsWithoutInventory);

        } catch (Exception e) {
            logger.error("Error at RequestsWithoutInventory: " + e.getMessage());
        }
    }
}
