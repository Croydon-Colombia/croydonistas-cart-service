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

import com.croydon.model.dao.QuotesDao;
import com.croydon.model.entity.Quotes;
import com.croydon.service.INewQuotes;
import com.croydon.service.IQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */

@Service
public class QuotesImpl implements IQuotes {

    @Autowired
    private QuotesDao quotesDao;
    @Autowired
    private INewQuotes newQuotesService;
    
    @Transactional
    @Override
    public Quotes save(Quotes quotes) {
        return quotesDao.save(quotes);
    }

    @Transactional()
    @Override
    public Quotes findByCustomersId(String id) {
        Quotes quotesFind = quotesDao.findByCustomersId(id);
        if(quotesFind == null){
            return newQuotesService.makeNewQuotes(id);
        }else{
            return quotesFind;
        }         
    }

    @Transactional
    @Override
    public void delete(Quotes quotes) {
        quotesDao.delete(quotes);
    }
    
}
