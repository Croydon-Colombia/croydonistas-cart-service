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

import com.croydon.exceptions.ForbiddenUserException;
import com.croydon.model.dao.CustomersDao;
import com.croydon.model.entity.Customers;
import org.springframework.cache.annotation.Cacheable;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose
 */

@Service
public class UserDetailserviceImpl implements UserDetailsService {

    @Autowired
    private CustomersDao customersDao;

    @Override
    @Cacheable(cacheNames = "userDetailsCache", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     
          Optional<Customers> customer = this.customersDao.findByDocumentNumber(username);
      
        if (customer.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        
        if(!customer.get().isEnabled()){
         throw new ForbiddenUserException();
        }
        return UserDetailsImpl.build(customer.get(), customer.get().getPassword());
    }

}
