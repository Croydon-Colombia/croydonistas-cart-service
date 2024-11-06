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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
public class ShoppingCartItemDto {
    
    @NotNull
    @NotBlank
    public long quotes_id;
    
    @NotNull
    @NotBlank
    public String productSku;
    
    
    public int quantity;
    
    // Campo opcional para indicar si es solo actualización (puede ser null)
    private Boolean isUpdateOnly = false;
    
}
