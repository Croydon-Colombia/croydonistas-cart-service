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
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name = "quote_incentive_items")
public class QuoteIncentiveItems {
    
    @EmbeddedId
    public QuoteIncentiveItemsPK quoteIncentiveItemsPK;

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
    @Column(name = "incentives")
    public double incentives;

    @Basic(optional = false)
    @Column(name = "line_number")
    public int lineNumber;

    @Basic(optional = false)
    @Column(name = "name")
    public String name;

    @Basic(optional = false)
    @Column(name = "qty")
    public int qty;

    @Column(name = "thumbnail")
    public String thumbnail;

    @Basic(optional = false)
    @Column(name = "total")
    public double total;

    @JoinColumn(name = "quotes_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    public Quotes quotes;

}
