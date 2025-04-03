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
package com.croydon.model.dao;

import com.croydon.model.entity.Products;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
public interface ProductsDao extends CrudRepository<Products, String> {

    @Query("SELECT q FROM Products q WHERE q.id = :sku AND q.incentive = true")
    Products findIncetiveBySku(@Param("sku") String sku);

    @Query("SELECT p FROM Products p WHERE p.id = :sku "
            + "OR (p.substituteCode <> '' AND p.substituteCode = "
            + "(SELECT p2.substituteCode FROM Products p2 WHERE p2.id = :sku AND p2.substituteCode <> '')) "
            + "OR p.substituteCode = :sku")
    List<Products> findProductWithSubstitutes(@Param("sku") String sku);

}
