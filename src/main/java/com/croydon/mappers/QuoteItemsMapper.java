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
import com.croydon.model.entity.QuoteItems;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre las entidades de elementos de
 * carrito de compras y sus DTO correspondientes. Utiliza MapStruct para generar
 * automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface QuoteItemsMapper {

    /**
     * Instancia única de QuoteItemsMapper para la creación de instancias.
     */
    QuoteItemsMapper INSTANCE = Mappers.getMapper(QuoteItemsMapper.class);

    /**
     * Convierte una entidad de elementos de carrito de compras en su DTO
     * correspondiente.
     *
     * @param quoteItems La entidad de elementos de carrito de compras a
     * convertir.
     * @return El DTO de elementos de carrito de compras resultante.
     */
    QuoteItemsDto quoteItemsToQuoteItemsDto(QuoteItems quoteItems);

    /**
     * Convierte un DTO de elementos de carrito de compras en su entidad
     * correspondiente.
     *
     * @param quoteItemsDto El DTO de elementos de carrito de compras a
     * convertir.
     * @return La entidad de elementos de carrito de compras resultante.
     */
    QuoteItems quoteItemsDtoToQuoteItems(QuoteItemsDto quoteItemsDto);
}
