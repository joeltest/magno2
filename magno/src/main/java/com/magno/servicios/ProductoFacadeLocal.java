/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.beans.ProductoVo;
import com.magno.magno.entity.Producto;
import com.magno.vo.ProductoListaVo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface ProductoFacadeLocal {

    void create(Producto producto);

    void edit(Producto producto);

    void remove(Producto producto);

    Producto find(Object id);

    List<Producto> findAll();
    
    List<Producto> findAllToday();
    
    List<ProductoListaVo> findAllPrincipal();

    List<Producto> findAllProductos(int idSucursal);
    
    List<Producto> findAllProductos(int idSucursal,int idCategoria);

    List<Producto> findRange(int[] range);

    int count();
    
}
