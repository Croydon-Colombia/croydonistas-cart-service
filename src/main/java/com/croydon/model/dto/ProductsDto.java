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
public class ProductsDto {
    
    public String id;

    public Date createdAt;

    public Date updatedAt;

    public String metaDescription;

    public String metaKeywords;

    public String metaTitle;

    public String name;

    public String urlKey;

    public Date activeFrom;

    public Date activeTo;

    public String baseImage;

    public Double customerDiscount;

    public String description;

    public Double employeeDiscount;

    public boolean enabled;

    public boolean enabledIncentive;

    public Boolean incentive;

    public Double levelIncentive;

    public boolean loadedImages;

    public String parentProduct;

    public Double price;

    public Double pricePum;

    public int productType;

    public boolean requireIndex;

    public String searchTerms;
    
    public String shortDescription;

    public Double specialPrice;

    public Boolean syncImages;

    public double taxPercent;

    public String unitPum;

    public String visibility;

    public boolean excluded;

    public Collection<CategoriesDto> categoriesCollection;

    public Collection<ProductIndicesDto> productIndicesCollection;

    public Collection<ImagesDto> imagesCollection;

    public Collection<ProductAttributesDto> productAttributesCollection;

    public Collection<PricesDto> pricesCollection;

    public Collection<ConfigurableProductOptionsDto> configurableProductOptionsCollection;

    public Collection<ConfigurableProductLinksDto> configurableProductLinksCollection;
}
