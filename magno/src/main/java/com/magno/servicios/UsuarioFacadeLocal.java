/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface UsuarioFacadeLocal extends FacadeLocal<Usuario>{

    List<Usuario> findAllEntity();

    Usuario login(String usuario,String clave,int idSucursal);
    
    
    
}
