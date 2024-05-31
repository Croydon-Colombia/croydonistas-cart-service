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
package com.croydon.service.implementation;

import com.croydon.model.dao.CustomersDao;
import com.croydon.model.entity.Customers;
import com.croydon.service.ICustomers;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar operaciones relacionadas con clientes.
 *
 * Esta clase implementa la interfaz ICustomers y proporciona métodos para
 * buscar clientes por ID.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class CustomersImpl implements ICustomers {

    @Autowired
    CustomersDao customersDao;

    /**
     * Busca un cliente por su ID.
     *
     * @param id el ID del cliente a buscar.
     * @return un Optional que contiene el cliente si se encuentra, o vacío si
     * no.
     */
    @Override
    public Optional<Customers> findById(String id) {
        return customersDao.findById(id);
    }

}
