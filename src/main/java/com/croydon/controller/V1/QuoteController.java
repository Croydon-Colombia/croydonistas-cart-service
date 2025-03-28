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
package com.croydon.controller.V1;

import com.croydon.exceptions.IncentiveProductException;
import com.croydon.exceptions.ProductException;
import com.croydon.exceptions.ShippingAddressException;
import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.security.CustomerAuth;
import com.croydon.service.IIncentiveCartManager;
import com.croydon.service.IShoppingCartManager;
import com.croydon.utilities.ApiResponse;
import jakarta.validation.Valid;
import org.hibernate.exception.DataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@RestController
@RequestMapping("/shopping-cart/v1")
public class QuoteController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private IIncentiveCartManager incentiveCartManagerService;

    @Autowired
    private IShoppingCartManager shoppingCartManagerService;
    
    //@Autowired
    //private CustomerAuth customerAuth;

    @GetMapping("quotes/customer")
    public ResponseEntity<ApiResponse<QuotesDto>> findQuotesByClientId(@RequestParam("customerId") String customerId) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            //var customer = customerAuth.get(); // -> Descomentarizar en prod
            QuotesDto response = shoppingCartManagerService.getOrCreateCart(customerId);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success", null));
        } catch (DataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, ex.getMessage(), null));
        }

    }

    @PostMapping("quotes/delete")
    public ResponseEntity<ApiResponse<QuotesDto>> deleteQuote(@RequestParam("quotesId") Long quotesId) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            //var customer = customerAuth.get();
            shoppingCartManagerService.deleteQuote(quotesId);
            return ResponseEntity.ok(new ApiResponse<>(null, "Carrito de compras: " + quotesId + ", Eliminado!", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, ex.getMessage(), null));
        }
    }

    @PostMapping("products/add-or-update")
    public ResponseEntity<ApiResponse<QuotesDto>> addProduct(@Valid @RequestBody ShoppingCartItemDto itemsRequest) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            // var customer = customerAuth.get(); // -> Descomentarizar en prod
            QuotesDto response = shoppingCartManagerService.addOrUpdateCartProduct(itemsRequest);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success", null));
        } catch (ProductException | ShippingAddressException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(null, "Failed", ex.getMessage()));
        }
    }

    @PostMapping("products/delete")
    public ResponseEntity<ApiResponse<QuotesDto>> deleteProduct(@Valid @RequestBody ShoppingCartItemDto itemsRequest) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            //var customer = customerAuth.get();
            QuotesDto response = shoppingCartManagerService.deleteCartProduct(itemsRequest);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success", null));
        } catch (ShippingAddressException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(null, "Failed", ex.getMessage()));
        }
    }

    @PostMapping("incentives/add-or-update")
    public ResponseEntity<ApiResponse<QuotesDto>> addIncentiveProduct(@Valid @RequestBody ShoppingCartItemDto itemsRequest) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            //var customer = customerAuth.get();
            QuotesDto response = incentiveCartManagerService.addOrUpdateIncentiveProduct(itemsRequest);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success", null));
        } catch (IncentiveProductException | ProductException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(null, "Failed", ex.getMessage()));
        }
    }

    @PostMapping("incentives/delete")
    public ResponseEntity<ApiResponse<QuotesDto>> deleteIncentiveProduct(@Valid @RequestBody ShoppingCartItemDto itemsRequest) {
        try {
            // Obtener el cliente autenticado usando CustomerAuth
            //var customer = customerAuth.get();
            QuotesDto response = incentiveCartManagerService.deleteIncentiveProduct(itemsRequest);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success", null));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(null, "Failed", ex.getMessage()));
        }
    }
}
