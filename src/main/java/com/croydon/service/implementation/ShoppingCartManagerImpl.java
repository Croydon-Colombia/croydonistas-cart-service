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
import com.croydon.mappers.QuotesMapper;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Products;
import com.croydon.model.entity.Quotes;
import com.croydon.service.INewQuotes;
import com.croydon.service.IProducts;
import com.croydon.service.IQuotes;
import com.croydon.service.IShoppingCartManager;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private IProducts productsComponent;

    @Override
    public QuotesDto getOrCreateCart(String customerId) {
        
        Quotes quote = quotesService.findByCustomersId(customerId);
        Quotes quotesResponse = (quote == null) ? newQuotesService.makeNewQuotes(customerId) : quote;
        return quotesMapper.quotesToQuotesDto(quotesResponse);
        
    }

    @Override
    public QuotesDto addOrUpdateCartProduct(ShoppingCartItemDto shoppingCartItemRequest){
        try {
            Products dbProduct = productsComponent.findProductById(shoppingCartItemRequest.productSku);
            // -> Continuar validar si ya es agregado -> si no nuevo -> calcular totales... ANY
        } catch (ProductException ex) {
            Logger.getLogger(ShoppingCartManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null; //-> cambiar esto por una custom exception 'CrudProductException'
        }
    }

}
