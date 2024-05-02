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
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Mapper
public interface QuoteTotalsPKMapper {
    QuoteTotalsPKMapper INSTANCE = Mappers.getMapper(QuoteTotalsPKMapper.class);
    
    QuoteTotalsPKDto quoteTotalsPKToQuoteTotalsPKDto(QuoteTotalsPK pk);
    
    QuoteTotalsPK quoteTotalsPKDtoToQuoteTotalsPK(QuoteTotalsPKDto pk);
}
