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
}
