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

import com.croydon.model.dao.CustomerTokenDao;
import com.croydon.model.entity.Customers;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jose- jose.rivera@croydon.com.co
 */
@Component
public class JwtUtilsCustomers {

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private CustomerTokenDao customerTokendao;

    public Customers getUserNameFromJwtToken(String token) {
        var authToken = this.customerTokendao.findByToken(token);
        if (authToken.isPresent() && authToken.get().isEnabled()) {
            return authToken.get().getCustomersId();
        }
        return null;
    }

    public boolean  validateToken( String token)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
              IllegalArgumentException e  ) {
            System.out.println( e.getMessage()+";"+ e.getClass().getName());
        }
        return false;
    }
    
}
