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

import com.croydon.model.dto.QuoteIncentiveItemsDto;
import com.croydon.model.entity.Images;
import com.croydon.model.entity.Products;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre entidades de productos y DTOs
 * de elementos de incentivo en el carrito de compras. Utiliza MapStruct para
 * generar automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface ProductsToQuotesItemsIncentiveMapper {

    /**
     * Instancia única de ProductsToQuotesItemsIncentiveMapper para la creación
     * de instancias.
     */
    ProductsToQuotesItemsIncentiveMapper INSTANCE
            = Mappers.getMapper(ProductsToQuotesItemsIncentiveMapper.class);

    /**
     * Convierte una entidad de Products en su DTO correspondiente de elementos
     * de incentivo en el carrito de compras.
     *
     * @param products La entidad de Products a convertir.
     * @return El DTO de elementos de incentivo en el carrito de compras
     * resultante.
     */
    @Mapping(target = "incentives", source = "levelIncentive")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "thumbnail", source = "imagesCollection")
    QuoteIncentiveItemsDto ProductsToQuoteIncentiveItemsDto(Products products);

    /**
     * Método de mapeo personalizado que convierte una colección de imágenes en
     * su representación de miniatura.
     *
     * @param imagesCollection La colección de imágenes.
     * @return La miniatura de la primera imagen en la colección, o null si no
     * hay imágenes.
     */
    default String mapImagesToThumbnail(Collection<Images> imagesCollection) {
        if (imagesCollection != null && !imagesCollection.isEmpty()) {
            Images image = imagesCollection.stream().findFirst().orElse(null);
            return image != null ? image.getFile() : null;
        }
        return null;
    }
}
