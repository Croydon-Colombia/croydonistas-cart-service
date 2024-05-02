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

import com.croydon.exceptions.ProductException;
import com.croydon.model.dao.ProductsDao;
import com.croydon.model.entity.Products;
import com.croydon.service.IProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class ProductsImpl implements IProducts {

    @Autowired
    private ProductsDao productsDao;
    
    @Override
    public Products findProductById(String id) throws ProductException{
        
        return productsDao.findById(id)
                            .orElseThrow(() -> 
                            new ProductException("Producto " + id +" no encontrado en DB"));
        
    }
    
}
