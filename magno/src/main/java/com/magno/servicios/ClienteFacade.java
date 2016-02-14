/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public Cliente login(String usuario, String clave) {
        Cliente c = null;

        try {

            c = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.correo = :usuario AND c.contrasena = :clave AND c.eliminado = 'False'")
                    .setParameter("usuario", usuario)
                    .setParameter("clave", clave)
                    .getSingleResult();

        } catch (NoResultException w) {
            return c;
        }
        return c;
    }

}
