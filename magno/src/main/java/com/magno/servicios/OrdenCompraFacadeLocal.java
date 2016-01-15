/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.beans.ProductoVo;
import com.magno.magno.entity.Cliente;
import com.magno.magno.entity.OrdenCompra;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface OrdenCompraFacadeLocal {

    void create(OrdenCompra ordenCompra);

    void edit(OrdenCompra ordenCompra);

    void remove(OrdenCompra ordenCompra);

    OrdenCompra find(Object id);

    List<OrdenCompra> findAll();
 
    
    List<OrdenCompra> findAll(int idEstatus);

    List<OrdenCompra> findRange(int[] range);

    int count();
    
    OrdenCompra guardarOrden(List<ProductoVo> listaProcProductoVo,double importeTotal,Cliente cliente,int idSucursal);
    
}
