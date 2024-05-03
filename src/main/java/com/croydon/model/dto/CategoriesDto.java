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

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

@Data
public class CategoriesDto {
    
    public Long id;

    public Date createdAt;

    public Date updatedAt;

    public String metaDescription;

    public String metaKeywords;

    public String metaTitle;

    public String name;

    public String urlKey;

    public String code;

    public String content;

    public boolean enabled;

    public BigInteger parent;

    public Collection<CatalogProductsDto> catalogProductsCollection;

}
