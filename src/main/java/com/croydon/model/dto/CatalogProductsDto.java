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

import java.util.Collection;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
public class CatalogProductsDto {
    
    public String id;

    public Date createdAt;

    public Date updatedAt;

    public String metaDescription;

    public String metaKeywords;

    public String metaTitle;

    public String name;

    public String urlKey;

    public boolean active;

    public String baseImage;

    public String description;

    public Double finalMaxPrice;

    public Double finalMinPrice;

    public Double maxPrice;

    public Double maxSpecialPrice;

    public Double minPrice;

    public Double minSpecialPrice;

    public String searchTerms;

    public String searchTermsString;

    public String shortDescription;

    public Double taxPercent;

    public String visibility;

    public Double levelIncentive;

    public Collection<CatalogProductAttributesDto> catalogProductAttributesCollection;
    
}
