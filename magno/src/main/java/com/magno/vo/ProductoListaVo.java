/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.vo;

import com.magno.magno.entity.Producto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jorodriguez
 */
public class ProductoListaVo {

    public ProductoListaVo(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }
    
    
    @Getter
    @Setter
    List<Producto> listaProducto;
    
}
