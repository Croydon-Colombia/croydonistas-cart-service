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
@Table(name = "addresses")
public class Addresses {
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

    @Column(name = "address")
    public String address;

    @Basic(optional = false)
    @Column(name = "billing")
    public boolean billing;

    @Basic(optional = false)
    @Column(name = "email")
    public String email;

    @Basic(optional = false)
    @Column(name = "first_name")
    public String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    public String lastName;

    @Column(name = "middle_name")
    public String middleName;

    @Column(name = "phone")
    public String phone;

    @Column(name = "shan")
    public String shan;

    @Basic(optional = false)
    @Column(name = "shipping")
    public boolean shipping;

    @Column(name = "suffix_name")
    public String suffixName;

    @JoinColumn(name = "cities_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public Cities citiesId;

    @JoinColumn(name = "customers_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public Customers customersId;
}
