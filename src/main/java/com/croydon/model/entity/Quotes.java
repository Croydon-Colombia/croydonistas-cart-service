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
package com.croydon.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "quotes")
public class Quotes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Column(name = "available_credit_limit")
    public Double availableCreditLimit;

    @Column(name = "available_incentives")
    public Double availableIncentives;

    @Column(name = "discount_amount")
    public Double discountAmount;

    @Column(name = "discount_code")
    public String discountCode;

    @Column(name = "discount_percent")
    public Double discountPercent;

    @Column(name = "document_company")
    public String documentCompany;

    @Column(name = "document_number")
    public String documentNumber;

    @Column(name = "document_type_code")
    public String documentTypeCode;

    @Column(name = "employee_limit_used")
    public Double employeeLimitUsed;

    @Column(name = "employee_quote_limit")
    public Double employeeQuoteLimit;

    @Basic(optional = false)
    @Column(name = "grand_total")
    public double grandTotal;

    @Column(name = "has_incentives")
    public Boolean hasIncentives;

    @Basic(optional = false)
    @Column(name = "incentives")
    public boolean incentives;

    @Basic(optional = false)
    @Column(name = "increment_id")
    public String incrementId;

    @Column(name = "ip")
    public String ip;

    @Basic(optional = false)
    @Column(name = "line_number")
    public int lineNumber;

    @Column(name = "requested_incentives")
    public Double requestedIncentives;

    @Column(name = "required_credit_limit")
    public Double requiredCreditLimit;

    @Column(name = "sale")
    public Boolean sale;

    @Basic(optional = false)
    @Column(name = "shipping_amount_base")
    public double shippingAmountBase;

    @Basic(optional = false)
    @Column(name = "shipping_incl_tax")
    public double shippingInclTax;

    @Basic(optional = false)
    @Column(name = "shipping_tax_amount")
    public double shippingTaxAmount;

    @Basic(optional = false)
    @Column(name = "shipping_value")
    public double shippingValue;

    @Basic(optional = false)
    @Column(name = "subtotal")
    public double subtotal;

    @Basic(optional = false)
    @Column(name = "tax_amount")
    public double taxAmount;

    @Basic(optional = false)
    @Column(name = "total_base")
    public double totalBase;

    @Basic(optional = false)
    @Column(name = "total_incl_tax")
    public double totalInclTax;

    @Column(name = "user_agent")
    public String userAgent;

    @Basic(optional = false)
    @Column(name = "available")
    public boolean available;

    @Column(name = "total_qty")
    public Integer totalQty;

    @Column(name = "total_qty_incentives")
    public Integer totalQtyIncentives;

    @Column(name = "cancelled_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date cancelledAt;

    @Column(name = "employee_limit_pending")
    public Double employeeLimitPending;

    @Column(name = "shipping_line_number")
    public Integer shippingLineNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    public Collection<QuoteTotals> quoteTotalsCollection;

    @JoinColumn(name = "customers_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customers customersId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    public Collection<QuoteItems> quoteItemsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    public Collection<QuoteIncentiveItems> quoteIncentiveItemsCollection;

}
