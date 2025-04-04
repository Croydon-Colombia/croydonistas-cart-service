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
package com.croydon.model.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
public class QuoteItemsDto {

    public QuoteItemsPKDto quoteItemsPK;

    public Date createdAt;

    public Date updatedAt;

    public boolean added;

    public Boolean appliedDiscount;

    public boolean appliedEmployeeDiscount;

    public double basePrice;

    public double basePriceInclTax;

    public double basePriceJde;

    public Integer category;

    public double discountPrice;

    public boolean haveEmployeeLines;

    public int lineNumber;

    public String name;

    public double originalBasePrice;

    public double originalPercentDiscount;

    public double percentDiscount;

    public Double precioPum;

    public double priceInclTax;

    public double priceToSend;

    public int qty;

    public double quoteLimitApplied;

    public boolean sendPrice;

    public double taxAmount;

    public String taxCode;

    public double taxPercent;

    public String thumbnail;

    public double total;

    public double totalBasePrice;

    public double totalDiscount;

    public double totalInclTax;

    public double totalOriginalBasePrice;

    public double totalTaxAmount;

    public String unidadPum;

    public List<EmployeeLineItemsDto> employeeLineItemsCollection;

    public String substituteCode;
    
    public Boolean isSubstitute;

    public QuoteItemsDto copyFrom(QuoteItemsDto source) {
        this.quoteItemsPK = source.quoteItemsPK;
        this.createdAt = source.createdAt;
        this.updatedAt = source.updatedAt;
        this.added = source.added;
        this.appliedDiscount = source.appliedDiscount;
        this.appliedEmployeeDiscount = source.appliedEmployeeDiscount;
        this.basePrice = source.basePrice;
        this.basePriceInclTax = source.basePriceInclTax;
        this.basePriceJde = source.basePriceJde;
        this.category = source.category;
        this.discountPrice = source.discountPrice;
        this.haveEmployeeLines = source.haveEmployeeLines;
        this.lineNumber = source.lineNumber;
        this.name = source.name;
        this.originalBasePrice = source.originalBasePrice;
        this.originalPercentDiscount = source.originalPercentDiscount;
        this.percentDiscount = source.percentDiscount;
        this.precioPum = source.precioPum;
        this.priceInclTax = source.priceInclTax;
        this.priceToSend = source.priceToSend;
        this.quoteLimitApplied = source.quoteLimitApplied;
        this.sendPrice = source.sendPrice;
        this.taxAmount = source.taxAmount;
        this.taxCode = source.taxCode;
        this.taxPercent = source.taxPercent;
        this.thumbnail = source.thumbnail;
        this.unidadPum = source.unidadPum;
        this.substituteCode = source.substituteCode;
        this.isSubstitute = source.isSubstitute;

        // Agregar los valores sumables
        this.qty = source.qty;
        this.total = source.total;
        this.totalBasePrice = source.totalBasePrice;
        this.totalDiscount = source.totalDiscount;
        this.totalInclTax = source.totalInclTax;
        this.totalOriginalBasePrice = source.totalOriginalBasePrice;
        this.totalTaxAmount = source.totalTaxAmount;

        return this;
    }
}
