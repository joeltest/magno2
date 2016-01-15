/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.OrdenCompra;
import com.magno.magno.entity.Venta;
import com.magno.vo.VentaVo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorodriguez
 */
@Local
public interface VentaFacadeLocal {

    void create(Venta venta);

    void edit(Venta venta);

    void remove(Venta venta);

    Venta find(Object id);
    
    Venta findByOrden(int idOrden);

    List<Venta> findAll();

    List<Venta> findRange(int[] range);
    
    void crearVenta(OrdenCompra orden);

       
    List<VentaVo> findAll(int idSucursal,Date fechaInicio,Date fechaFin);
    
//    void cancelarVenta(int idVenta,int idUsuario);
    
    int count();
    
}
