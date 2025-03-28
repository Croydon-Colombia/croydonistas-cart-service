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

import java.util.ArrayList;
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

    // Constructor de copia
    public QuotesDto(QuotesDto other) {
        this.id = other.id;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
        this.availableCreditLimit = other.availableCreditLimit;
        this.availableIncentives = other.availableIncentives;
        this.discountAmount = other.discountAmount;
        this.discountCode = other.discountCode;
        this.discountPercent = other.discountPercent;
        this.documentCompany = other.documentCompany;
        this.documentNumber = other.documentNumber;
        this.documentTypeCode = other.documentTypeCode;
        this.employeeLimitUsed = other.employeeLimitUsed;
        this.employeeQuoteLimit = other.employeeQuoteLimit;
        this.grandTotal = other.grandTotal;
        this.hasIncentives = other.hasIncentives;
        this.incentives = other.incentives;
        this.incrementId = other.incrementId;
        this.ip = other.ip;
        this.lineNumber = other.lineNumber;
        this.requestedIncentives = other.requestedIncentives;
        this.requiredCreditLimit = other.requiredCreditLimit;
        this.sale = other.sale;
        this.shippingAmountBase = other.shippingAmountBase;
        this.shippingInclTax = other.shippingInclTax;
        this.shippingTaxAmount = other.shippingTaxAmount;
        this.shippingValue = other.shippingValue;
        this.subtotal = other.subtotal;
        this.taxAmount = other.taxAmount;
        this.totalBase = other.totalBase;
        this.totalInclTax = other.totalInclTax;
        this.userAgent = other.userAgent;
        this.available = other.available;
        this.totalQty = other.totalQty;
        this.totalQtyIncentives = other.totalQtyIncentives;
        this.cancelledAt = other.cancelledAt;
        this.employeeLimitPending = other.employeeLimitPending;
        this.shippingLineNumber = other.shippingLineNumber;

        this.quoteTotalsCollection = (other.quoteTotalsCollection != null) ? new ArrayList<>(other.quoteTotalsCollection) : new ArrayList<>();
        this.quoteItemsCollection = (other.quoteItemsCollection != null) ? new ArrayList<>(other.quoteItemsCollection) : new ArrayList<>();
        this.quoteIncentiveItemsCollection = (other.quoteIncentiveItemsCollection != null) ? new ArrayList<>(other.quoteIncentiveItemsCollection) : new ArrayList<>();

        this.customersId = other.customersId;
    }
}
