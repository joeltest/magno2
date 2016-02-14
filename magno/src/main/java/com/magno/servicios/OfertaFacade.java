/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Oferta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class OfertaFacade extends AbstractFacade<Oferta> implements OfertaFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OfertaFacade() {
        super(Oferta.class);
    }

    @Override
    public List<Oferta> listaOfertas(int idSucursal) {
        return em.createQuery("SELECT o FROM Oferta o WHERE o.sucursalId.id = :idSucursal AND o.eliminado='False'")
                .setParameter("idSucursal", idSucursal)
                .getResultList();
    }
    
}
