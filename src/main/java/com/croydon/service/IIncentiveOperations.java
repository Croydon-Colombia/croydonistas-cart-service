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
package com.croydon.service;

import com.croydon.exceptions.IncentiveProductException;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Products;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
public interface IIncentiveOperations {

    void isIncentiveSumValid(QuotesDto quotesDto, Products dbProduct, ShoppingCartItemDto shoppingCartItemRequest, double incentiveBalance) throws IncentiveProductException;
    void isIncentiveUpdateSumValid(QuotesDto quotesDto, Products dbProduct, ShoppingCartItemDto shoppingCartItemRequest, double incentiveBalance) throws IncentiveProductException;
}
