/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.vo;

import com.magno.magno.entity.DetalleOrdenCompra;
import com.magno.magno.entity.OrdenCompra;
import com.magno.magno.entity.Venta;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jorodriguez
 */
public class VentaVo {
    
    @Getter
    @Setter
    private Venta venta;
    
    @Getter
    @Setter
    private List<DetalleOrdenCompra> detalle;
    
}
