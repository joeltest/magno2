/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.DetalleOferta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface DetalleOfertaFacadeLocal {

    void create(DetalleOferta detalleOferta);

    void edit(DetalleOferta detalleOferta);

    void remove(DetalleOferta detalleOferta);

    DetalleOferta find(Object id);

    List<DetalleOferta> findAll();

    List<DetalleOferta> findRange(int[] range);

    int count();
    
}
