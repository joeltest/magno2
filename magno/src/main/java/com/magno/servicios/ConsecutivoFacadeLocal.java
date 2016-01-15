/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Consecutivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface ConsecutivoFacadeLocal {

    void create(Consecutivo consecutivo);

    void edit(Consecutivo consecutivo);

    void remove(Consecutivo consecutivo);

    Consecutivo find(Object id);

    List<Consecutivo> findAll();

    List<Consecutivo> findRange(int[] range);

    int count();
    
    String obtenerConsecutivo(String nombreConsecutivo, int idSucursal);
    
}
