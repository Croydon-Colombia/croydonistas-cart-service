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

import com.croydon.model.dao.QuoteItemsDao;
import com.croydon.model.entity.QuoteItems;
import com.croydon.service.IQuoteItems;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Service
public class QuoteItemsImpl implements IQuoteItems {

    @Autowired
    QuoteItemsDao quoteItemsDao;
    
    @Transactional
    @Override
    public QuoteItems save(QuoteItems quoteItems) {
        return quoteItemsDao.save(quoteItems);
    }

    @Transactional
    @Override
    public void delete(QuoteItems quoteItems) {
        quoteItemsDao.delete(quoteItems);
    }

    @Transactional
    @Override
    public void deleteAll(List<QuoteItems> quoteItems) {
        quoteItemsDao.deleteAll(quoteItems);
    }

    @Transactional
    @Override
    public void saveAll(List<QuoteItems> quoteItems) {
        quoteItemsDao.saveAll(quoteItems);
    }
    
}
