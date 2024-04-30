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
    private Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "available_credit_limit")
    private Double availableCreditLimit;

    @Column(name = "available_incentives")
    private Double availableIncentives;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "document_company")
    private String documentCompany;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "document_type_code")
    private String documentTypeCode;

    @Column(name = "employee_limit_used")
    private Double employeeLimitUsed;

    @Column(name = "employee_quote_limit")
    private Double employeeQuoteLimit;

    @Basic(optional = false)
    @Column(name = "grand_total")
    private double grandTotal;

    @Column(name = "has_incentives")
    private Boolean hasIncentives;

    @Basic(optional = false)
    @Column(name = "incentives")
    private boolean incentives;

    @Basic(optional = false)
    @Column(name = "increment_id")
    private String incrementId;

    @Column(name = "ip")
    private String ip;

    @Basic(optional = false)
    @Column(name = "line_number")
    private int lineNumber;

    @Column(name = "requested_incentives")
    private Double requestedIncentives;

    @Column(name = "required_credit_limit")
    private Double requiredCreditLimit;

    @Column(name = "sale")
    private Boolean sale;

    @Basic(optional = false)
    @Column(name = "shipping_amount_base")
    private double shippingAmountBase;

    @Basic(optional = false)
    @Column(name = "shipping_incl_tax")
    private double shippingInclTax;

    @Basic(optional = false)
    @Column(name = "shipping_tax_amount")
    private double shippingTaxAmount;

    @Basic(optional = false)
    @Column(name = "shipping_value")
    private double shippingValue;

    @Basic(optional = false)
    @Column(name = "subtotal")
    private double subtotal;

    @Basic(optional = false)
    @Column(name = "tax_amount")
    private double taxAmount;

    @Basic(optional = false)
    @Column(name = "total_base")
    private double totalBase;

    @Basic(optional = false)
    @Column(name = "total_incl_tax")
    private double totalInclTax;

    @Column(name = "user_agent")
    private String userAgent;

    @Basic(optional = false)
    @Column(name = "available")
    private boolean available;

    @Column(name = "total_qty")
    private Integer totalQty;

    @Column(name = "total_qty_incentives")
    private Integer totalQtyIncentives;

    @Column(name = "cancelled_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledAt;

    @Column(name = "employee_limit_pending")
    private Double employeeLimitPending;

    @Column(name = "shipping_line_number")
    private Integer shippingLineNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    private Collection<QuoteTotals> quoteTotalsCollection;

    //@JoinColumn(name = "customers_id", referencedColumnName = "id")
    //@ManyToOne(optional = false)
    @Column(name = "customers_id")
    private String customersId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    private Collection<QuoteItems> quoteItemsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotes")
    private Collection<QuoteIncentiveItems> quoteIncentiveItemsCollection;

}
