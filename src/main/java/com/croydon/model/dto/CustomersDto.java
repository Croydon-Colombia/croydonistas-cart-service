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
package com.croydon.model.dto;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
public class CustomersDto {
    
    public String id;

    public Date createdAt;

    public Date updatedAt;

    public BigInteger billingAddressId;

    public boolean changedPassword;

    public Double discount;

    public String documentNumber;

    public String email;

    public boolean employee;

    public boolean enabled;

    public boolean exempt;

    public String firstName;

    public String lastName;

    public String middleName;

    public String password;

    public String phone;

    public BigInteger shippingAddressId;

    public String suffixName;

    public Collection<AddressesDto> addressesCollection;

}
