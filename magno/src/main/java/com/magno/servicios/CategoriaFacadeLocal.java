/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Categoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface CategoriaFacadeLocal extends FacadeLocal<Categoria>{

    List<Categoria> findAllMenu();
    
    List<Categoria> findAllMenuChild(int idCategoria);
    
    
    
}
