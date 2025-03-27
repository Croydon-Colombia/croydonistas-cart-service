package com.croydon.security;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Jose
 */
@Configuration
public class CorsConfig {

    /**
     * Configura la política CORS para la aplicación.
     *
     * - Permite solicitudes desde cualquier origen (configurado con '*'). -
     * Especifica los métodos HTTP permitidos: GET, POST, PUT, DELETE, OPTIONS.
     * - Permite todas las cabeceras. - Permite el uso de credenciales (cookies,
     * cabeceras de autorización, etc.).
     *
     * @return La configuración de CORS que será aplicada a todas las rutas de
     * la aplicación.
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Cambia según necesidad
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
