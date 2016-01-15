/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.Producto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author jorodriguez
 */
@ToString
@Getter
@Setter
public class ProductoVo {

    @Getter
    @Setter
    private Producto producto;
    @Getter
    @Setter
    private int cantidad;
    @Getter
    @Setter
    private Double subTotal;

}
