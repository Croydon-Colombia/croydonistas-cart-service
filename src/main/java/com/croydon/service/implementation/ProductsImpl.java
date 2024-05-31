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

import com.croydon.exceptions.IncentiveProductException;
import com.croydon.exceptions.ProductException;
import com.croydon.model.dao.ProductsDao;
import com.croydon.model.entity.Products;
import com.croydon.service.IProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementación del servicio de gestión de productos.
 *
 * Esta clase implementa la interfaz IProducts y proporciona métodos para buscar
 * productos por ID y SKU, así como para buscar productos de incentivo por SKU.
 *
 * @autor Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Component
public class ProductsImpl implements IProducts {

    @Autowired
    private ProductsDao productsDao;

    /**
     * Busca un producto por su ID en la base de datos.
     *
     * @param id el ID del producto a buscar.
     * @return el producto encontrado.
     * @throws ProductException si el producto no se encuentra en la base de
     * datos.
     */
    @Override
    public Products findProductById(String id) throws ProductException {

        return productsDao.findById(id)
                .orElseThrow(()
                        -> new ProductException("Producto " + id + " no encontrado en DB"));

    }

    /**
     * Busca un producto de incentivo por su SKU en la base de datos.
     *
     * @param sku el SKU del producto de incentivo a buscar.
     * @return el producto de incentivo encontrado.
     * @throws IncentiveProductException si el SKU no corresponde a un producto
     * de incentivo.
     */
    @Override
    public Products findIncetiveBySku(String sku) throws IncentiveProductException {

        Products product = productsDao.findIncetiveBySku(sku);
        if (product == null) {
            throw new IncentiveProductException("No es un incentivo: " + sku);
        }
        return product;

    }

}
