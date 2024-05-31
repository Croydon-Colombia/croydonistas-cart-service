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

import com.croydon.model.dto.ProductsDto;
import com.croydon.model.entity.Products;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre entidades de productos y sus
 * DTOs asociados. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface ProductsMapper {

    /**
     * Instancia única de ProductsMapper para la creación de instancias.
     */
    ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    /**
     * Convierte una entidad de Products a su DTO correspondiente.
     *
     * @param products La entidad de Products a convertir.
     * @return El DTO de Products resultante.
     */
    ProductsDto ProductsToProductsDto(Products products);

    /**
     * Convierte un DTO de Products a su entidad correspondiente.
     *
     * @param products El DTO de Products a convertir.
     * @return La entidad de Products resultante.
     */
    Products ProductsDtoToProducts(ProductsDto products);
}
