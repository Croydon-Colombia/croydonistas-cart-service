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
package com.croydon.mappers;

import com.croydon.model.dto.QuoteItemsDto;
import com.croydon.model.entity.Images;
import com.croydon.model.entity.Products;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface ProductsToQuotesItemsMapper {

    ProductsToQuotesItemsMapper INSTANCE
            = Mappers.getMapper(ProductsToQuotesItemsMapper.class);

    @Mapping(target = "precioPum", source = "pricePum")
    @Mapping(target = "unidadPum", source = "unitPum")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "thumbnail", source = "imagesCollection")
    @Mapping(target = "taxPercent", source = "taxPercent")
    @Mapping(target = "basePrice", expression = "java(mapPrice(products))")
    @Mapping(target = "originalBasePrice", expression = "java(mapPrice(products))")
    @Mapping(target = "originalPercentDiscount", expression = "java(mapOriginalPercentDiscount(products))")
    QuoteItemsDto ProductsToQuoteItemsDto(Products products);
    
    
    
    default double mapOriginalPercentDiscount(Products product){
        if (product.getCustomerDiscount() != null) {
            return product.getCustomerDiscount();
        }
        return 0;
    }

    default String mapImagesToThumbnail(Collection<Images> imagesCollection) {
        if (imagesCollection != null && !imagesCollection.isEmpty()) {
            Images image = imagesCollection.stream().findFirst().orElse(null);
            return image != null ? image.getFile() : null;
        }
        return null;
    }

    default double mapPrice(Products product) {
        if (product.getSpecialPrice() != null && product.getSpecialPrice() > 0) {
            return product.getSpecialPrice();
        } else {
            return product.getPrice();
        }
    }
}
