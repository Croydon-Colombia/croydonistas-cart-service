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
 * Esta interfaz define la lógica de mapeo entre entidades de productos y DTOs
 * de elementos de carrito de compras. Utiliza MapStruct para generar
 * automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface ProductsToQuotesItemsMapper {

    /**
     * Instancia única de ProductsToQuotesItemsMapper para la creación de
     * instancias.
     */
    ProductsToQuotesItemsMapper INSTANCE
            = Mappers.getMapper(ProductsToQuotesItemsMapper.class);

    /**
     * Convierte una entidad de Products en su DTO correspondiente de elementos
     * de carrito de compras.
     *
     * @param products La entidad de Products a convertir.
     * @return El DTO de elementos de carrito de compras resultante.
     */
    @Mapping(target = "precioPum", source = "pricePum")
    @Mapping(target = "unidadPum", source = "unitPum")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "thumbnail", source = "imagesCollection")
    @Mapping(target = "taxPercent", source = "taxPercent")
    @Mapping(target = "basePrice", expression = "java(mapPrice(products))")
    @Mapping(target = "originalBasePrice", expression = "java(mapPrice(products))")
    @Mapping(target = "originalPercentDiscount", expression = "java(mapOriginalPercentDiscount(products))")
    QuoteItemsDto ProductsToQuoteItemsDto(Products products);

    /**
     * Método de mapeo personalizado que asigna el descuento porcentual original
     * a 0 si es nulo.
     *
     * @param product La entidad de Products.
     * @return El descuento porcentual original o 0 si es nulo.
     */
    default double mapOriginalPercentDiscount(Products product) {
        if (product.getCustomerDiscount() != null) {
            return product.getCustomerDiscount();
        }
        return 0;
    }

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

    /**
     * Método de mapeo personalizado que selecciona el precio especial si está
     * disponible, de lo contrario, selecciona el precio estándar.
     *
     * @param product La entidad de Products.
     * @return El precio especial si está disponible, de lo contrario, el precio
     * estándar.
     */
    default double mapPrice(Products product) {
        if (product.getSpecialPrice() != null && product.getSpecialPrice() > 0) {
            return product.getSpecialPrice();
        } else {
            return product.getPrice();
        }
    }
}
