/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario login(String usuario, String clave, int idSucursal) {
            Usuario usu = (Usuario) em.createNamedQuery("Usuario.login")
                    .setParameter("alias", usuario)
                    .setParameter("clave", clave)
                    .setParameter("idSucursal", idSucursal)
                    .getSingleResult();
        return usu;
    }

    @Override
    public List<Usuario> findAllEntity() {
        return em.createNamedQuery("Usuario.findAll").getResultList();
    }
    
}
