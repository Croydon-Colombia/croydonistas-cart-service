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
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface CitiesMapper {
    CitiesMapper INSTANCE = Mappers.getMapper(CitiesMapper.class);
    
    CitiesDto CitiesToCitiesDto(Cities cities);
    
    Cities CitiesDtoToCities(CitiesDto cities);
}
