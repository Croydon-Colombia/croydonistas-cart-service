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

import com.croydon.model.dto.QuoteTotalsDto;
import com.croydon.model.entity.QuoteTotals;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre la entidad QuoteTotals y su DTO
 * correspondiente QuoteTotalsDto. Utiliza MapStruct para generar
 * automáticamente las implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface QuoteTotalsMapper {

    /**
     * Instancia única de QuoteTotalsMapper para la creación de instancias.
     */
    QuoteTotalsMapper INSTANCE = Mappers.getMapper(QuoteTotalsMapper.class);

    /**
     * Convierte una entidad QuoteTotals en su DTO correspondiente.
     *
     * @param quotes La entidad QuoteTotals a convertir.
     * @return El DTO de QuoteTotals resultante.
     */
    QuoteTotalsDto quoteTotalsToQuoteTotalsDto(QuoteTotals quotes);

    /**
     * Convierte un DTO de QuoteTotals en su entidad correspondiente.
     *
     * @param quotes El DTO de QuoteTotals a convertir.
     * @return La entidad QuoteTotals resultante.
     */
    QuoteTotals quotesTotalsDtoToQuoteTotals(QuoteTotalsDto quotes);

    /**
     * Convierte una lista de DTO de QuoteTotals en una lista de entidades
     * QuoteTotals.
     *
     * @param quotes La lista de DTO de QuoteTotals a convertir.
     * @return La lista de entidades QuoteTotals resultante.
     */
    List<QuoteTotals> listQuotesTotalsDtoToQuoteTotals(List<QuoteTotalsDto> quotes);

}
