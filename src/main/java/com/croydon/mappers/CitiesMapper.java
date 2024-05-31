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

import com.croydon.model.dto.CitiesDto;
import com.croydon.model.entity.Cities;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre entidades de ciudades y sus
 * DTOs asociados. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface CitiesMapper {

    /**
     * Instancia única de CitiesMapper para la creación de instancias.
     */
    CitiesMapper INSTANCE = Mappers.getMapper(CitiesMapper.class);

    /**
     * Convierte una entidad de Cities a su DTO correspondiente.
     *
     * @param cities La entidad de Cities a convertir.
     * @return El DTO de Cities resultante.
     */
    CitiesDto CitiesToCitiesDto(Cities cities);

    /**
     * Convierte un DTO de Cities a su entidad correspondiente.
     *
     * @param cities El DTO de Cities a convertir.
     * @return La entidad de Cities resultante.
     */
    Cities CitiesDtoToCities(CitiesDto cities);
}
