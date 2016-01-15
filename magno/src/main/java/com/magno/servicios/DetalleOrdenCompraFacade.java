/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.DetalleOrdenCompra;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class DetalleOrdenCompraFacade extends AbstractFacade<DetalleOrdenCompra> implements DetalleOrdenCompraFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleOrdenCompraFacade() {
        super(DetalleOrdenCompra.class);
    }

    @Override
    public List<DetalleOrdenCompra> findByOrden(int idOrden) {
        return em.createNamedQuery("DetalleOrdenCompra.findAllByIdeOrden")
                .setParameter("idOrden", idOrden)
                .getResultList();
    }

    @Override
    public List<DetalleOrdenCompra> findByVenta(int idVenta) {
        return em.createQuery("SELECT d FROM DetalleOrdenCompra d WHERE d.venta.id = :idVenta")
                .setParameter("idVenta", idVenta)
                .getResultList();
    }
    
}
