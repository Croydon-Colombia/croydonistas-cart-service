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

import com.croydon.model.dao.QuoteTotalsDao;
import com.croydon.model.entity.QuoteTotals;
import com.croydon.model.entity.QuoteTotalsPK;
import com.croydon.service.IQuoteTotals;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para gestionar los totales del carrito de
 * compras.
 *
 * Esta clase implementa la interfaz IQuoteTotals y proporciona métodos para
 * buscar y guardar los totales del carrito de compras.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuoteTotalsImpl implements IQuoteTotals {

    @Autowired
    QuoteTotalsDao quoteTotalsService;

    /**
     * Busca los totales del carrito de compras por su clave primaria.
     *
     * @param pk la clave primaria de los totales del carrito de compras.
     * @return un Optional que contiene los totales del carrito de compras si
     * están presentes, de lo contrario vacío.
     */
    @Override
    public Optional<QuoteTotals> findByPk(QuoteTotalsPK pk) {
        return quoteTotalsService.findById(pk);
    }

    /**
     * Guarda todos los totales del carrito de compras.
     *
     * @param totals la lista de totales del carrito de compras a guardar.
     */
    @Override
    public void saveAll(List<QuoteTotals> totals) {
        quoteTotalsService.saveAll(totals);
    }

}
