/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import java.util.List;

/**
 *
 * @author ihsa
 */
public interface FacadeLocal<T> {
    
    void create(T entitdad);

    void edit(T entitdad);

    void remove(T entitdad);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();
    
}
