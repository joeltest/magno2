/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.SiArchivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface SiArchivoFacadeLocal {

    void create(SiArchivo siArchivo);

    void edit(SiArchivo siArchivo);

    void remove(SiArchivo siArchivo);

    SiArchivo find(Object id);

    List<SiArchivo> findAll();

    List<SiArchivo> findRange(int[] range);

    int count();
    
}
