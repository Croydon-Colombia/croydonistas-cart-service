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

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Jose Rivera - jose.rivera@croydon.com.co
 */
@Data
@Entity
@Table(name="customers")
public class Customers {
     @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)    
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)    
    private Date updatedAt;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "suffix_name")
    private String suffixName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "employee")
    private boolean employee;

    @Column(name = "changed_password", nullable = false)
    private boolean changedPassword;

    @Column(name = "exempt")
    private Boolean exempt = false;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "shipping_address_id")
    private Long shippingAddressId;

    @Column(name = "billing_address_id")
    private Long billingAddressId;
   
 
}
