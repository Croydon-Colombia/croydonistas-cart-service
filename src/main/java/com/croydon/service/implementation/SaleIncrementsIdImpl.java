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

import com.croydon.model.dao.SaleIncrementsIdDao;
import com.croydon.model.entity.SaleIncrementsId;
import com.croydon.service.ISaleIncrementsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para gestionar los incrementos de ID de venta.
 *
 * Esta clase implementa la interfaz ISaleIncrementsId y proporciona un método
 * para guardar un incremento de ID de venta en la base de datos.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class SaleIncrementsIdImpl implements ISaleIncrementsId {

    @Autowired
    private SaleIncrementsIdDao saleIncrementsIdDaoService;

    /**
     * Guarda un incremento de ID de venta en la base de datos.
     *
     * @param saleIncrementsId el incremento de ID de venta a guardar.
     * @return el incremento de ID de venta guardado.
     */
    @Transactional
    @Override
    public SaleIncrementsId save(SaleIncrementsId saleIncrementsId) {
        return saleIncrementsIdDaoService.save(saleIncrementsId);
    }

}
