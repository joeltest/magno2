/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Consecutivo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class ConsecutivoFacade extends AbstractFacade<Consecutivo> implements ConsecutivoFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsecutivoFacade() {
        super(Consecutivo.class);
    }
    
    
    @Override
    public String obtenerConsecutivo(String nombreConsecutivo, int idSucursal) {
        Consecutivo consecutivo = (Consecutivo) em.createNamedQuery("Consecutivo.findByNombreSucursal")
                .setParameter("nombre", nombreConsecutivo).setParameter("idSucursal", idSucursal).getSingleResult();
        
        String codigo="" ;
        
        consecutivo.setValor(consecutivo.getValor()+1);

        edit(consecutivo);
        
        codigo = consecutivo.getCodigo()+"-"+consecutivo.getValor();
        
        return codigo;        
    }
    
}
