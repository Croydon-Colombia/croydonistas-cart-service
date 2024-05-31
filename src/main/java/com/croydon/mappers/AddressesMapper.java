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

import com.croydon.model.dto.AddressesDto;
import com.croydon.model.entity.Addresses;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Esta interfaz define la lógica de mapeo entre entidades de direcciones y sus
 * DTOs asociados. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo.
 *
 * Autor: Edwin Torres Email: edwin.torres@croydon.com.co
 */
@Mapper(componentModel = "spring")
public interface AddressesMapper {

    /**
     * Instancia única de AddressesMapper para la creación de instancias.
     */
    AddressesMapper INSTANCE = Mappers.getMapper(AddressesMapper.class);

    /**
     * Convierte una entidad de Addresses a su DTO correspondiente.
     *
     * @param address La entidad de Addresses a convertir.
     * @return El DTO de Addresses resultante.
     */
    AddressesDto AddressesToAddressesDto(Addresses address);

    /**
     * Convierte un DTO de Addresses a su entidad correspondiente.
     *
     * @param address El DTO de Addresses a convertir.
     * @return La entidad de Addresses resultante.
     */
    Addresses AddressesDtoToAddresses(AddressesDto address);

    /**
     * Convierte una lista de entidades de Addresses a una lista de sus DTOs
     * correspondientes.
     *
     * @param address La lista de entidades de Addresses a convertir.
     * @return La lista de DTOs de Addresses resultante.
     */
    List<AddressesDto> AddressesToAddressesDto(List<Addresses> address);

    /**
     * Convierte una lista de DTOs de Addresses a una lista de sus entidades
     * correspondientes.
     *
     * @param address La lista de DTOs de Addresses a convertir.
     * @return La lista de entidades de Addresses resultante.
     */
    List<Addresses> AddressesDtoToAddresses(List<AddressesDto> address);

}
