/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Categoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }
     @Override
    public List<Categoria> findAllMenu() {
        return em.createNamedQuery("Categoria.findAllMenu",Categoria.class).getResultList();
        
    }

    @Override
    public List<Categoria> findAllMenuChild(int idCategoria) {
        return em.createNamedQuery("Categoria.findAllMenuChild",Categoria.class).setParameter("idCategoria", idCategoria).getResultList();
    }

}
