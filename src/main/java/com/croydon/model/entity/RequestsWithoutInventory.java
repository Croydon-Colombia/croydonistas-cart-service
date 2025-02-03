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
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "requests_without_inventory")
public class RequestsWithoutInventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public Long id;
    
    @Basic(optional = false)
    @Column(name = "customer_id")
    public String customerId;
    
    @Basic(optional = false)
    @Column(name = "sku")
    public String sku;
    
    @Basic(optional = false)
    @Column(name = "qty_requests")
    public int qtyRequests;
    
    @Basic(optional = true)
    @Column(name = "qty_available")
    public int qtyAvailable;
    
    @Basic(optional = true)
    @Column(name = "product_type")
    public String productType;
    
    @Basic(optional = false)
    @Column(name = "quotes_id")
    public long quotesId;
    
    @Basic(optional = true)
    @Column(name = "event_type")
    public String eventType;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;
    
    @Basic(optional = false)
    @Column(name = "reported")
    public boolean reported;
    
    @Column(name = "reported_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date reportedAt;
}
