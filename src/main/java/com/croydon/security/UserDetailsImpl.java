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

import com.croydon.model.entity.Customers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 *  @author Jose- jose.rivera@croydon.com.co
 */

public class UserDetailsImpl implements UserDetails {

    
     protected Customers customer;
    protected String username;
    @JsonIgnore
    protected String password;
    protected Collection<? extends GrantedAuthority> authorities;
    public UserDetailsImpl(Customers customer, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.customer = customer;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    public static UserDetailsImpl build(Customers customer, String password) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserDetailsImpl(customer, customer.getDocumentNumber(), password, authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;       
    }
    @Override
    public String getPassword() {
       return this.password;
    }
    @Override
    public String getUsername() {
      return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
     return true;
    }
    @Override
    public boolean isAccountNonLocked() {
     return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
     return true;   
    }
    @Override
    public boolean isEnabled() {
     return this.customer.isEnabled();
    }
    
     @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(username, user.getUsername());
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.authorities);
        return hash;
    }
    @Override
    public String toString() {
        return this.username;
    }
    
}
