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

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "quote_items")
public class QuoteItems {
   
    @EmbeddedId
    protected QuoteItemsPK quoteItemsPK;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Basic(optional = false)
    @Column(name = "added")
    private boolean added;

    @Column(name = "applied_discount")
    private Boolean appliedDiscount;

    @Basic(optional = false)
    @Column(name = "applied_employee_discount")
    private boolean appliedEmployeeDiscount;

    @Basic(optional = false)
    @Column(name = "base_price")
    private double basePrice;

    @Basic(optional = false)
    @Column(name = "base_price_incl_tax")
    private double basePriceInclTax;

    @Basic(optional = false)
    @Column(name = "base_price_jde")
    private double basePriceJde;

    @Column(name = "category")
    private Integer category;

    @Basic(optional = false)
    @Column(name = "discount_price")
    private double discountPrice;

    @Basic(optional = false)
    @Column(name = "have_employee_lines")
    private boolean haveEmployeeLines;

    @Basic(optional = false)
    @Column(name = "line_number")
    private int lineNumber;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "original_base_price")
    private double originalBasePrice;

    @Basic(optional = false)
    @Column(name = "original_percent_discount")
    private double originalPercentDiscount;

    @Basic(optional = false)
    @Column(name = "percent_discount")
    private double percentDiscount;

    @Column(name = "precio_pum")
    private Double precioPum;

    @Basic(optional = false)
    @Column(name = "price_incl_tax")
    private double priceInclTax;

    @Basic(optional = false)
    @Column(name = "price_to_send")
    private double priceToSend;

    @Basic(optional = false)
    @Column(name = "qty")
    private int qty;

    @Basic(optional = false)
    @Column(name = "quote_limit_applied")
    private double quoteLimitApplied;

    @Basic(optional = false)
    @Column(name = "send_price")
    private boolean sendPrice;

    @Basic(optional = false)
    @Column(name = "tax_amount")
    private double taxAmount;

    @Basic(optional = false)
    @Column(name = "tax_code")
    private String taxCode;

    @Basic(optional = false)
    @Column(name = "tax_percent")
    private double taxPercent;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Basic(optional = false)
    @Column(name = "total")
    private double total;

    @Basic(optional = false)
    @Column(name = "total_base_price")
    private double totalBasePrice;

    @Basic(optional = false)
    @Column(name = "total_discount")
    private double totalDiscount;

    @Basic(optional = false)
    @Column(name = "total_incl_tax")
    private double totalInclTax;

    @Basic(optional = false)
    @Column(name = "total_original_base_price")
    private double totalOriginalBasePrice;

    @Basic(optional = false)
    @Column(name = "total_tax_amount")
    private double totalTaxAmount;

    @Column(name = "unidad_pum")
    private String unidadPum;

    @JoinColumn(name = "quotes_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private Quotes quotes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quoteItems")
    private Collection<EmployeeLineItems> employeeLineItemsCollection;

}
