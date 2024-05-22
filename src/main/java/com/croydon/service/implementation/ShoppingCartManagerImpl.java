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
import com.croydon.model.entity.Customers;
import com.croydon.model.entity.Products;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IAddQuoteItem;
import com.croydon.service.ICustomers;
import com.croydon.service.INewQuotes;
import com.croydon.service.IProducts;
import com.croydon.service.IQuotes;
import com.croydon.service.IShoppingCartManager;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.croydon.service.ICollectsQuoteTotals;
import reactor.core.publisher.Mono;

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
    private IAddQuoteItem IAddQuoteItemService;

    @Autowired
    private QuotesMapper quotesMapper;

    @Autowired
    private ProductsToQuotesItemsMapper productsToQuotesItemsMapper;

    @Autowired
    private IProducts productsComponent;

    @Autowired
    private ICollectsQuoteTotals collectsQuoteTotalsService;

    @Autowired
    private ICustomers customersService;
    
    @Autowired
    private IStockClient stockClient;

    @Override
    public QuotesDto getOrCreateCart(String customerId) {
        Optional<Customers> customer = customersService.findById(customerId);
        Quotes quote = quotesService.findByCustomersId(customer.get());
        Quotes quotesResponse = (quote == null) ? newQuotesService.makeNewQuotes(customerId) : quote;
        quotesService.save(quotesResponse);
        return quotesMapper.quotesToQuotesDto(quotesResponse);

    }

    @Override
    public QuotesDto addOrUpdateCartProduct(ShoppingCartItemDto shoppingCartItemRequest) {
        try {
            //NOTA: Antes de hacer operaciones se debe validar inventario disponnible en JDE

            StockResponse stockResponse = stockClient.getStock(shoppingCartItemRequest.productSku, "CP001").block();
            if(stockResponse.qty < shoppingCartItemRequest.quantity)
                throw new ProductException("Cantidad solicitada no disponible");
                                
            Products dbProduct = productsComponent.findProductById(shoppingCartItemRequest.productSku);
            Quotes dbQuotes = quotesService.findByQuotesId(shoppingCartItemRequest.quotes_id);

            QuoteItemsDto quoteItemsDto = productsToQuotesItemsMapper.ProductsToQuoteItemsDto(dbProduct);
            QuotesDto quotesDto = quotesMapper.quotesToQuotesDto(dbQuotes);

            quoteItemsDto.setQuoteItemsPK(setQuoteItemsPKDto(quotesDto, dbProduct));

            boolean itemExists = quotesDto.getQuoteItemsCollection().stream()
                    .anyMatch(item -> item.getQuoteItemsPK().equals(quoteItemsDto.getQuoteItemsPK()));

            if (itemExists) {
                //El articulo YA existe en el carrito
                //NOTA: Implementar logica para actualizar articulo
            } else {
                //El articulo NO existe en el carrito
                
                quoteItemsDto.setQty(shoppingCartItemRequest.quantity);
                QuotesDto updatedQuotesDto = IAddQuoteItemService.addNewQuoteItem(quotesDto, quoteItemsDto, dbProduct);
                QuotesDto quoteDtoWithTotals = collectsQuoteTotalsService.quotesDto(updatedQuotesDto);

                quotesService.save(quotesMapper.quotesDtoToQuotes(quoteDtoWithTotals));
                quotesDto = quoteDtoWithTotals;
            }
            return quotesDto;
            
        } catch (ProductException ex) {
            Logger.getLogger(ShoppingCartManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null; //-> cambiar esto por una custom exception 'CrudProductException'
        } catch (ShippingAddressException ex) {
            Logger.getLogger(ShoppingCartManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private QuoteItemsPKDto setQuoteItemsPKDto(QuotesDto quotesDto, Products dbProduct) {
        QuoteItemsPKDto quoteItemsPKDto = new QuoteItemsPKDto();

        quoteItemsPKDto.setCustomersId(quotesDto.getCustomersId().getId());
        quoteItemsPKDto.setQuotesId(quotesDto.getId());
        quoteItemsPKDto.setSku(dbProduct.getId());

        return quoteItemsPKDto;
    }

}
