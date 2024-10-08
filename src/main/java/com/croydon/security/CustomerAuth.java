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
package com.croydon.security;

import com.croydon.exceptions.UnauthorizedException;
import com.croydon.model.dao.CustomersDao;
import com.croydon.model.entity.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose
 */
@Service
public class CustomerAuth {

    @Autowired
    private CustomersDao customerDao;

    public Customers get() {
        String username = getToken();
        return this.customerDao.findByDocumentNumber(username).orElseThrow(UnauthorizedException::new);
    }

    public String getToken() {
        var context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
