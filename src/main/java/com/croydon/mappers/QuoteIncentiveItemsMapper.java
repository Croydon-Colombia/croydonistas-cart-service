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
import com.croydon.model.entity.QuoteIncentiveItems;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Mapper
public interface QuoteIncentiveItemsMapper {
    QuoteIncentiveItemsMapper INSTANCE = Mappers.getMapper(QuoteIncentiveItemsMapper.class);
    
    QuoteIncentiveItemsDto quoteIncentiveItemsToQuoteIncentiveItemsDto(QuoteIncentiveItems quoteIncentiveItems);
    QuoteIncentiveItems quoteIncentiveItemsDtoToQuoteIncentiveItems(QuoteIncentiveItemsDto quoteIncentiveItems);
}
