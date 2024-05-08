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
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigInteger;
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
@Table(name = "customers")
public class Customers {
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    public String id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Column(name = "billing_address_id")
    public BigInteger billingAddressId;

    @Basic(optional = false)
    @Column(name = "changed_password")
    public boolean changedPassword;

    @Column(name = "discount")
    public Double discount;

    @Basic(optional = false)
    @Column(name = "document_number")
    public String documentNumber;

    @Basic(optional = false)
    @Column(name = "email")
    public String email;

    @Basic(optional = false)
    @Column(name = "employee")
    public boolean employee;

    @Basic(optional = false)
    @Column(name = "enabled")
    public boolean enabled;

    @Basic(optional = false)
    @Column(name = "exempt")
    public boolean exempt;

    @Basic(optional = false)
    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "middle_name")
    public String middleName;

    @Basic(optional = false)
    @Column(name = "password")
    public String password;

    @Basic(optional = false)
    @Column(name = "phone")
    public String phone;

    @Column(name = "shipping_address_id")
    public BigInteger shippingAddressId;

    @Column(name = "suffix_name")
    public String suffixName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    public Collection<Addresses> addressesCollection;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    //public Collection<Sales> salesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    public Collection<Quotes> quotesCollection;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    //public Collection<Referrals> referralsCollection;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    //public Collection<CustomersTokens> customersTokensCollection;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    //public Collection<RequestChangeData> requestChangeDataCollection;

    //@JoinColumn(name = "document_types_id", referencedColumnName = "id")
    //@ManyToOne(optional = false)
    //public DocumentTypes documentTypesId;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "customersId")
    //public Collection<Wishlists> wishlistsCollection;

}
