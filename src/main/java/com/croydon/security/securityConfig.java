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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Jose - jose.rivera@croydon.com.co
 */
@Configuration
@EnableWebSecurity
public class securityConfig {
    
      @Autowired
    private CustomerAuth customerAuth;



//    @Autowired
//    protected void configure(HttpSecurity http) throws Exception {
//        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter((UserDetailsService) customerAuth);
//        
//        http
//            .csrf().disable() // Deshabilitar CSRF (si no es necesario)
//            .authorizeRequests()
//                .anyRequest().permitAll() // Permitir acceso sin autenticación a patrones específicos
//                .anyRequest().authenticated() // Requiere autenticación para todas las demás solicitudes
//            .and()
//            .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Gestión de sesiones sin estado
//        
//          http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);// Agregar filtro personalizado de autenticación JWT
//    }
    
    
       @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().permitAll() // Permitir acceso sin autenticación a todas las solicitudes
            .and()
            .csrf().disable(); // Deshabilitar CSRF (si no es necesario)
            
        return http.build();
    }
//    
//     @Bean
//    public AuthenticationTokenFilter authenticationTokenFilter() throws Exception {
//        AuthenticationTokenFilter filter = new AuthenticationTokenFilter(customerAuth);
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}
