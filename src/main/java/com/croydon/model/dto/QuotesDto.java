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

import java.util.Collection;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
public class QuotesDto {
    
    public Long id;

    public Date createdAt;

    public Date updatedAt;

    public Double availableCreditLimit;

    public Double availableIncentives;

    public Double discountAmount;

    public String discountCode;

    public Double discountPercent;

    public String documentCompany;

    public String documentNumber;

    public String documentTypeCode;

    public Double employeeLimitUsed;

    public Double employeeQuoteLimit;

    public double grandTotal;

    public Boolean hasIncentives;

    public boolean incentives;

    public String incrementId;

    public String ip;

    public int lineNumber;

    public Double requestedIncentives;

    public Double requiredCreditLimit;

    public Boolean sale;

    public double shippingAmountBase;

    public double shippingInclTax;

    public double shippingTaxAmount;

    public double shippingValue;

    public double subtotal;

    public double taxAmount;

    public double totalBase;

    public double totalInclTax;

    public String userAgent;

    public boolean available;

    public Integer totalQty;

    public Integer totalQtyIncentives;

    public Date cancelledAt;

    public Double employeeLimitPending;

    public Integer shippingLineNumber;

    public Collection<QuoteTotalsDto> quoteTotalsCollection;

    public CustomersDto customersId;

    public Collection<QuoteItemsDto> quoteItemsCollection;

    public Collection<QuoteIncentiveItemsDto> quoteIncentiveItemsCollection;
}
