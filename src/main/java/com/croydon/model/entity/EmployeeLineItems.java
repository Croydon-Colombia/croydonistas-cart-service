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
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "employee_line_items")
public class EmployeeLineItems {
    
    @EmbeddedId
    public EmployeeLineItemsPK employeeLineItemsPK;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Basic(optional = false)
    @Column(name = "added")
    public boolean added;

    @Basic(optional = false)
    @Column(name = "applied_employee_discount")
    public boolean appliedEmployeeDiscount;

    @Basic(optional = false)
    @Column(name = "base_price")
    public double basePrice;

    @Basic(optional = false)
    @Column(name = "base_price_incl_tax")
    public double basePriceInclTax;

    @Basic(optional = false)
    @Column(name = "base_price_jde")
    public double basePriceJde;

    @Basic(optional = false)
    @Column(name = "discount_price")
    public double discountPrice;

    @Basic(optional = false)
    @Column(name = "line_number")
    public int lineNumber;

    @Basic(optional = false)
    @Column(name = "original_base_price")
    public double originalBasePrice;

    @Basic(optional = false)
    @Column(name = "original_percent_discount")
    public double originalPercentDiscount;

    @Basic(optional = false)
    @Column(name = "percent_discount")
    public double percentDiscount;

    @Basic(optional = false)
    @Column(name = "price_incl_tax")
    public double priceInclTax;

    @Basic(optional = false)
    @Column(name = "price_to_send")
    public double priceToSend;

    @Basic(optional = false)
    @Column(name = "qty")
    public int qty;

    @Basic(optional = false)
    @Column(name = "tax_amount")
    public double taxAmount;

    @Basic(optional = false)
    @Column(name = "tax_percent")
    public double taxPercent;

    @Basic(optional = false)
    @Column(name = "total")
    public double total;

    @Basic(optional = false)
    @Column(name = "total_base_price")
    public double totalBasePrice;

    @Basic(optional = false)
    @Column(name = "total_discount")
    public double totalDiscount;

    @Basic(optional = false)
    @Column(name = "total_incl_tax")
    public double totalInclTax;

    @Basic(optional = false)
    @Column(name = "total_tax_amount")
    public double totalTaxAmount;

    @JoinColumns({
        @JoinColumn(name = "customers_id", referencedColumnName = "customers_id", insertable = false, updatable = false),
        @JoinColumn(name = "quotes_id", referencedColumnName = "quotes_id", insertable = false, updatable = false),
        @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    public QuoteItems quoteItems;
    
}
