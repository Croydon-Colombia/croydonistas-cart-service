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

import com.croydon.model.dao.ExcludedProductsDao;
import com.croydon.model.entity.ExcludedProducts;
import com.croydon.service.IExcludedProducts;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar productos excluidos.
 * 
 * Esta clase implementa la interfaz IExcludedProducts y proporciona métodos para
 * buscar productos excluidos por su SKU.
 * 
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class ExcludedProductsImpl implements IExcludedProducts{
    
    @Autowired
    private ExcludedProductsDao excludedProducts;

    
    /**
     * Busca un producto excluido por su SKU.
     *
     * @param sku el SKU del producto excluido a buscar.
     * @return un Optional que contiene el producto excluido si se encuentra, o vacío si no.
     */
    @Override
    public Optional<ExcludedProducts> findBySku(String sku) {
        return excludedProducts.findById(sku);
    }
}
