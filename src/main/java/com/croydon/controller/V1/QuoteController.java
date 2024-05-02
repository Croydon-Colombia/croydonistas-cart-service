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

import com.croydon.model.dto.QuotesDto;
import com.croydon.model.dto.ShoppingCartItemDto;
import com.croydon.service.IShoppingCartManager;
import com.croydon.utilities.ApiResponse;
import jakarta.validation.Valid;
import org.hibernate.exception.DataException;
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
    
    @Autowired
    private IShoppingCartManager shoppingCartManagerService;
    
    /*
    Getters methods
    */
    @GetMapping("quotes/customer/{customerId}")
    public ResponseEntity<ApiResponse<QuotesDto>> findQuotesByClientId(@PathVariable("customerId") String customerId){
        try {
            QuotesDto response = shoppingCartManagerService.getOrCreateCart(customerId);
            return ResponseEntity.ok(new ApiResponse<>(response, "Success"));
        } catch (DataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, ex.getMessage()));
        }         
        
    }
    
    /*
    Post methods
    */
    @PostMapping("quotes/delete")
    public ResponseEntity<ApiResponse<QuotesDto>> deleteQuote (@RequestParam("quotesId") String quotesId){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Not implemented!"));
    }
    
    @PostMapping("products/add-or-update")
    public ResponseEntity<ApiResponse<QuotesDto>> addProduct(@Valid @RequestBody ShoppingCartItemDto itemsRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Not implemented!"));
    }
    
    @PostMapping("products/delete")
    public ResponseEntity<ApiResponse<QuotesDto>> deleteProduct(@RequestParam("productSku") String productSku){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Not implemented!"));
    }
}
