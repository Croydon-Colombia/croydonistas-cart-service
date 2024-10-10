 /*
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

import java.util.Date;
import lombok.Data;

/**
 *
 *  @author Jose-jose.rivera@croydon.com.co
 */
@Data
public class CustomerTokenDto {
    
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String device;
    private String deviceName;
    private boolean enabled;
    private String ip;
    private String token;
    private String userAgent;
    private String userAgentName;
    private String customerId;     
}
