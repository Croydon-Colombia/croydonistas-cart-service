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

import com.croydon.model.dto.QuoteTotalsPKDto;
import com.croydon.model.entity.QuoteTotalsPK;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre la clave primaria compuesta
 * QuoteTotalsPK y su DTO correspondiente QuoteTotalsPKDto. Utiliza MapStruct
 * para generar automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface QuoteTotalsPKMapper {

    /**
     * Instancia única de QuoteTotalsPKMapper para la creación de instancias.
     */
    QuoteTotalsPKMapper INSTANCE = Mappers.getMapper(QuoteTotalsPKMapper.class);

    /**
     * Convierte una clave primaria compuesta QuoteTotalsPK en su DTO
     * correspondiente.
     *
     * @param pk La clave primaria compuesta QuoteTotalsPK a convertir.
     * @return El DTO de QuoteTotalsPK resultante.
     */
    QuoteTotalsPKDto quoteTotalsPKToQuoteTotalsPKDto(QuoteTotalsPK pk);

    /**
     * Convierte un DTO de QuoteTotalsPK en su clave primaria compuesta
     * correspondiente.
     *
     * @param pk El DTO de QuoteTotalsPK a convertir.
     * @return La clave primaria compuesta QuoteTotalsPK resultante.
     */
    QuoteTotalsPK quoteTotalsPKDtoToQuoteTotalsPK(QuoteTotalsPKDto pk);
}
