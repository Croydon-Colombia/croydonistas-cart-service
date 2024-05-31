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

import com.croydon.model.entity.QuoteItemsPK;
import com.croydon.model.dto.QuoteItemsPKDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre las claves primarias de los
 * elementos del carrito de compras y sus DTO correspondientes. Utiliza
 * MapStruct para generar automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface QuoteItemsPKMapper {

    /**
     * Instancia única de QuoteItemsPKMapper para la creación de instancias.
     */
    QuoteItemsPKMapper INSTANCE = Mappers.getMapper(QuoteItemsPKMapper.class);

    /**
     * Convierte una clave primaria de elementos del carrito de compras en su
     * DTO correspondiente.
     *
     * @param quoteItemsPK La clave primaria de elementos del carrito de compras
     * a convertir.
     * @return El DTO de clave primaria de elementos del carrito de compras
     * resultante.
     */
    QuoteItemsPKDto quoteItemsPKToQuoteItemsPKDto(QuoteItemsPK quoteItemsPK);

    /**
     * Convierte un DTO de clave primaria de elementos del carrito de compras en
     * su clave primaria correspondiente.
     *
     * @param quoteItemsPKDto El DTO de clave primaria de elementos del carrito
     * de compras a convertir.
     * @return La clave primaria de elementos del carrito de compras resultante.
     */
    QuoteItemsPK quoteItemsPKDtoToQuoteItemsPK(QuoteItemsPKDto quoteItemsPK);
}
