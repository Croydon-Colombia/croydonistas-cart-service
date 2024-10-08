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

import com.croydon.model.dao.QuoteIncentiveItemsDao;
import com.croydon.model.entity.QuoteIncentiveItems;
import com.croydon.model.entity.QuoteIncentiveItemsPK;
import com.croydon.service.IQuoteIncentiveItems;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuoteIncentiveItemsImpl implements IQuoteIncentiveItems {

    @Autowired
    QuoteIncentiveItemsDao quoteIncentiveItemsService;

    @Transactional
    @Override
    public QuoteIncentiveItems save(QuoteIncentiveItems quoteIncentiveItems) {
        return quoteIncentiveItemsService.save(quoteIncentiveItems);
    }

    @Override
    public Optional<QuoteIncentiveItems> findByPk(QuoteIncentiveItemsPK quoteIncentiveItemsPK) {
        return quoteIncentiveItemsService.findById(quoteIncentiveItemsPK);
    }

    @Transactional
    @Override
    public void delete(QuoteIncentiveItems quoteIncentiveItems) {
        quoteIncentiveItemsService.delete(quoteIncentiveItems);
    }

}
