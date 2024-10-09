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
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
 *
 * @author Jose-jose.rivera@croydon.com.co
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers_tokens")
public class CustomerToken implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)   
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)    
    private Date updatedAt;
    @Column(name = "device")
    private String device;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "ip")
    private String ip;
    @Column(name = "token")
    private String token;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "user_agent_name")
    private String userAgentName;
    @JoinColumn(name = "customers_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Customers customersId;
}
