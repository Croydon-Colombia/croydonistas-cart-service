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

import com.croydon.model.dao.QuotesDao;
import com.croydon.model.entity.Customers;
import com.croydon.model.entity.Quotes;
import com.croydon.service.IQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
/**
 * Implementación del servicio para gestionar los carritos de compras.
 *
 * Esta clase implementa la interfaz IQuotes y proporciona métodos para guardar,
 * buscar y eliminar carritos de compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuotesImpl implements IQuotes {

    @Autowired
    private QuotesDao quotesDao;

    /**
     * Guarda un carrito de compras en la base de datos.
     *
     * @param quotes el carrito de compras a guardar.
     * @return el carrito de compras guardado.
     */
    @Transactional
    @Override
    public Quotes save(Quotes quotes) {
        return quotesDao.save(quotes);
    }

    /**
     * Busca un carrito de compras por el ID del cliente.
     *
     * @param customerId el cliente del carrito de compras.
     * @return el carrito de compras del cliente especificado.
     */
    @Transactional()
    @Override
    public Quotes findByCustomersId(Customers customerId) {
        return quotesDao.findByCustomersId(customerId);
    }

    /**
     * Elimina un carrito de compras de la base de datos.
     *
     * @param quotes el carrito de compras a eliminar.
     */
    @Transactional
    @Override
    public void delete(Quotes quotes) {
        quotesDao.delete(quotes);
    }

    /**
     * Busca un carrito de compras por su ID.
     *
     * @param id el ID del carrito de compras a buscar.
     * @return el carrito de compras con el ID especificado.
     */
    @Override
    public Quotes findByQuotesId(Long id) {
        return quotesDao.findById(id).orElseThrow();
    }

}
