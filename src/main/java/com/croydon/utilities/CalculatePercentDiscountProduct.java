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
package com.croydon.utilities;

import com.croydon.model.dto.CustomersDto;
import com.croydon.model.dto.ProductsDto;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
public class CalculatePercentDiscountProduct {

    public static double calculatePercent(CustomersDto customer, ProductsDto product) {
        if (product.getCustomerDiscount() != null) {
            return product.getCustomerDiscount();
        } else if (customer != null && customer.getDiscount() != null) {
            return customer.getDiscount();
        } else {
            return 0;
        }
    }
}
