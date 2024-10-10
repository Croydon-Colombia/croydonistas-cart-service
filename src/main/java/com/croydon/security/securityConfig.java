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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Jose
 */
@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private CustomerAuth customerAuth;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Autowired
    private AuthenticationTokenFilter authTokenFilter;

    // Configuración de la seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable() // Desactiva CORS y CSRF (si no es necesario)
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // Manejo de excepciones no autorizadas
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Sesiones sin estado (JWT)
                .authorizeRequests()
                .requestMatchers("/shopping-cart/v1/**").authenticated() // Rutas que requieren autenticación
                .anyRequest().permitAll();  // El resto de las rutas permiten acceso sin autenticación

        // Añadir el filtro antes del filtro de autenticación por defecto
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
