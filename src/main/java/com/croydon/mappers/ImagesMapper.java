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

import com.croydon.model.dto.ImagesDto;
import com.croydon.model.entity.Images;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre entidades de imágenes y sus
 * DTOs asociados. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface ImagesMapper {

    /**
     * Instancia única de ImagesMapper para la creación de instancias.
     */
    ImagesMapper INSTANCE = Mappers.getMapper(ImagesMapper.class);

    /**
     * Convierte una entidad de Images a su DTO correspondiente.
     *
     * @param images La entidad de Images a convertir.
     * @return El DTO de Images resultante.
     */
    ImagesDto ImagesToImagesDto(Images images);

    /**
     * Convierte un DTO de Images a su entidad correspondiente.
     *
     * @param images El DTO de Images a convertir.
     * @return La entidad de Images resultante.
     */
    Images ImagesDtoToImages(ImagesDto images);
}
