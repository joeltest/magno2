/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Producto;
import com.magno.vo.ProductoListaVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public List<Producto> findAllProductos(int idSucursal) {
        return em.createNamedQuery("Producto.findAllProductos").setParameter("idSucursal", idSucursal).getResultList();

    }

    @Override
    public List<Producto> findAllProductos(int idSucursal, int idCategoria) {
        System.out.println(" id suc" + idSucursal);
        System.out.println(" id catego" + idCategoria);
        return em.createNamedQuery("Producto.findAllProductosPorCategoria")
                .setParameter("idSucursal", idSucursal)
                .setParameter("idCategoria", idCategoria)
                .getResultList();
    }

    @Override
    public List<ProductoListaVo> findAllPrincipal() {
        List<ProductoListaVo> lista = new ArrayList<>();
        
        List<Producto> lista1 = em.createNamedQuery("Producto.findAllPrincipal")
                .setFirstResult(0)
                .setMaxResults(4)
                .getResultList();
        lista.add(new ProductoListaVo(lista1));
        List<Producto> lista2 = em.createNamedQuery("Producto.findAllPrincipal")
                .setFirstResult(4)
                .setMaxResults(8)
                .getResultList();
        lista.add(new ProductoListaVo(lista2));
        List<Producto> lista3 = em.createNamedQuery("Producto.findAllPrincipal")
                .setFirstResult(8)
                .setMaxResults(12)
                .getResultList();
        lista.add(new ProductoListaVo(lista3));
        
        return lista;
    }

    @Override
    public List<Producto> findAllToday() {
         List<Producto> lista1 = em.createNamedQuery("Producto.findAllPrincipal")
                .setFirstResult(0)
                .setMaxResults(6)
                .getResultList();
         Collections.reverse(lista1);  
         
         return lista1;
    }

}
