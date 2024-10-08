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
package com.croydon.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Clase utilitaria para manejar fechas.
 * 
 * Autor: Edwin Torres
 * Email: edwin.torres@croydon.com.co
 */
public class DateUtils {
    
    
    /**
     * Obtiene la fecha y hora actual.
     * 
     * @return La fecha y hora actual como un objeto Date.
     */
    public static Date getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
}
