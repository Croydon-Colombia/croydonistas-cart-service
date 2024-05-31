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
import com.croydon.model.dto.QuoteIncentiveItemsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.model.entity.Products;
import com.croydon.service.IIncentiveOperations;
import org.springframework.stereotype.Component;

/**
 * Implementación de operaciones relacionadas con incentivos.
 *
 * Esta clase implementa la interfaz IIncentiveOperations y proporciona métodos
 * para validar la suma de puntos de incentivo en un carrito de compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class IncentiveOperationsImpl implements IIncentiveOperations {

    /**
     * Valida si la suma de puntos de incentivo es válida en un carrito de
     * compras.
     *
     * @param quotesDto el DTO del carrito de compras.
     * @param dbProduct el producto relacionado con el producto de incentivo.
     * @param shoppingCartItemRequest el DTO del producto de incentivo que se
     * desea agregar.
     * @param incentiveBalance el saldo de incentivos disponible.
     * @throws IncentiveProductException si hay un problema con el producto de
     * incentivo.
     */
    @Override
    public void isIncentiveSumValid(QuotesDto quotesDto, Products dbProduct, ShoppingCartItemDto shoppingCartItemRequest, double incentiveBalance) throws IncentiveProductException {

        double pointsRequest = dbProduct.getLevelIncentive() * shoppingCartItemRequest.getQuantity();
        double totalRedeemedPoints = calculateTotalRedeemedPoints(quotesDto);

        double totalPoints = pointsRequest + totalRedeemedPoints;

        if (totalPoints > incentiveBalance) {
            throw new IncentiveProductException("Cupo de incentivos: " + incentiveBalance + ". Solicitado: " + totalPoints);
        }
    }

    /**
     * Calcula el total de puntos de incentivo redimidos en un carrito de
     * compras.
     *
     * @param cart el DTO del carrito de compras.
     * @return el total de puntos de incentivo redimidos.
     */
    private double calculateTotalRedeemedPoints(QuotesDto quotesDto) {
        return quotesDto.getQuoteIncentiveItemsCollection().stream()
                .mapToDouble(QuoteIncentiveItemsDto::getTotal)
                .sum();
    }
}
