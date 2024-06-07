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
import com.croydon.model.dto.AddressesDto;
import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.entity.ExcludedProducts;
import com.croydon.model.entity.Products;
import com.croydon.service.IExcludedProducts;
import com.croydon.service.IQuoteItemsTaxesCalculator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de cálculo de impuestos para los elementos del
 * carrito de compras.
 *
 * Esta clase implementa la interfaz IQuoteItemsTaxesCalculator y proporciona
 * métodos para calcular los totales de los elementos del carrito de compras,
 * teniendo en cuenta los impuestos.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuoteItemsTaxesCalculatorImpl implements IQuoteItemsTaxesCalculator {

    @Autowired
    private IExcludedProducts excludedProductsService;

    /**
     * Calcula los totales de un elemento del carrito de compras, teniendo en
     * cuenta los impuestos.
     *
     * @param quote el DTO del carrito de compras.
     * @param quoteItem el DTO del elemento del carrito de compras.
     * @param shippingAddress la dirección de envío del cliente.
     * @param products el producto asociado al elemento del carrito de compras.
     * @return el DTO del elemento del carrito de compras actualizado con los
     * totales.
     * @throws com.croydon.exceptions.ProductException
     */
    @Override
    public QuoteItemsDto calculateItemTotals(QuotesDto quote, QuoteItemsDto quoteItem, AddressesDto shippingAddress, Products products) throws ProductException {

        boolean isExemptCustomer = quote.getCustomersId().getExempt() != null && quote.getCustomersId().getExempt();
        boolean isExemptRegion = shippingAddress.getCitiesId().getRegionsId().getExempt();
        boolean isExcludedRegion = shippingAddress.getCitiesId().getRegionsId().getExcluded();
        boolean isExcludedCity = shippingAddress.getCitiesId().getExcluded();

        if (isExcludedCity || isExemptCustomer || isExcludedRegion) {
            withoutTax(quoteItem, products);
        } else {
            if (isExemptCustomer || isExcludedRegion) {
                withoutTax(quoteItem, products);
            } else if (isExemptCustomer || isExemptRegion) {
                Optional<ExcludedProducts> excludedItem
                        = excludedProductsService.findBySku(quoteItem.getQuoteItemsPK().getSku());
                if (excludedItem.isPresent()) {
                    withoutTax(quoteItem, products);
                } else {
                    calculateTax(quoteItem, products);
                }
            } else {
                calculateTax(quoteItem, products);
            }
        }

        return updateQuoteItemTotals(quoteItem);
    }

    /**
     * Calcula los totales de un elemento del carrito de compras incluyendo
     * impuestos.
     *
     * @param quoteItem el DTO del elemento del carrito de compras.
     * @param products el producto asociado al elemento del carrito de compras.
     * @return el DTO del elemento del carrito de compras actualizado con los
     * totales con impuestos.
     */
    private QuoteItemsDto calculateTax(QuoteItemsDto quoteItem, Products products) {

        double taxPercent = products.getTaxPercent() / 100;
        double discountPercent = products.getCustomerDiscount() / 100;

        double discountTotal = products.getPrice() * discountPercent;
        double basePrice = products.getPrice() - discountTotal;
        double basePriceTax = basePrice * taxPercent;
        double basePriceWithTax = basePrice + basePriceTax;

        quoteItem.setBasePrice(basePrice);
        quoteItem.setBasePriceInclTax(basePriceWithTax);
        quoteItem.setBasePriceJde(basePrice);
        quoteItem.setDiscountPrice(discountTotal);
        quoteItem.setPriceInclTax(basePriceWithTax);
        quoteItem.setTaxAmount(basePriceTax);

        quoteItem.setPercentDiscount(products.getCustomerDiscount());

        quoteItem.setTotalBasePrice(basePrice);
        quoteItem.setTotalDiscount(discountTotal);
        quoteItem.setTotalInclTax(basePriceWithTax);
        quoteItem.setTotalOriginalBasePrice(products.getPrice());
        quoteItem.setTotalTaxAmount(basePriceTax);
        quoteItem.setOriginalBasePrice(products.getPrice());

        return quoteItem;
    }

    /**
     * Calcula los totales de un elemento del carrito de compras sin incluir
     * impuestos.
     *
     * @param quoteItem el DTO del elemento del carrito de compras.
     * @param products el producto asociado al elemento del carrito de compras.
     * @return el DTO del elemento del carrito de compras actualizado con los
     * totales sin impuestos.
     */
    private QuoteItemsDto withoutTax(QuoteItemsDto quoteItem, Products products) throws ProductException {

        if (products.getCustomerDiscount() == null) {
            throw new ProductException("Descuento no definido para el producto: " + quoteItem.getQuoteItemsPK().getSku());
        }

        double discountPercent = products.getCustomerDiscount() / 100;

        double discountTotal = products.getPrice() * discountPercent;
        double basePrice = products.getPrice() - discountTotal;

        quoteItem.setBasePrice(basePrice);
        quoteItem.setBasePriceInclTax(basePrice);
        quoteItem.setBasePriceJde(basePrice);
        quoteItem.setDiscountPrice(discountTotal);
        quoteItem.setPriceInclTax(basePrice);
        quoteItem.setTaxAmount(0);

        quoteItem.setPercentDiscount(products.getCustomerDiscount());

        quoteItem.setTotalBasePrice(basePrice);
        quoteItem.setTotalDiscount(discountTotal);
        quoteItem.setTotalInclTax(basePrice);
        quoteItem.setTotalOriginalBasePrice(products.getPrice());
        quoteItem.setTotalTaxAmount(0);
        quoteItem.setOriginalBasePrice(products.getPrice());

        return quoteItem;
    }

    /**
     * Actualiza los totales del elemento del carrito de compras.
     *
     * @param quoteItem el DTO del elemento del carrito de compras.
     * @return el DTO del elemento del carrito de compras actualizado con los
     * totales.
     */
    private QuoteItemsDto updateQuoteItemTotals(QuoteItemsDto quoteItem) {
        quoteItem.setTotalBasePrice(quoteItem.getBasePrice() * quoteItem.getQty());
        quoteItem.setTotalDiscount(quoteItem.getDiscountPrice() * quoteItem.getQty());
        quoteItem.setTotalInclTax(quoteItem.getPriceInclTax() * quoteItem.getQty());
        quoteItem.setTotalTaxAmount(quoteItem.getTaxAmount() * quoteItem.getQty());
        quoteItem.setTotalOriginalBasePrice(quoteItem.getOriginalBasePrice() * quoteItem.getQty());
        return quoteItem;
    }
}
