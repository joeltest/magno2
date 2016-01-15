/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.SiArchivo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class SiArchivoFacade extends AbstractFacade<SiArchivo> implements SiArchivoFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SiArchivoFacade() {
        super(SiArchivo.class);
    }
    
}
