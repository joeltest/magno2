/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.DetalleOrdenCompra;
import com.magno.magno.entity.OrdenCompra;
import com.magno.servicios.DetalleOrdenCompraFacadeLocal;
import com.magno.servicios.OrdenCompraFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class ImprimirOrden implements Serializable {
    
    
    @ManagedProperty(value = "#{productoIniciarCompraBean}")
    private ProductoIniciarCompraBean productoIniciarCompraBean;
    
   @EJB
    private DetalleOrdenCompraFacadeLocal detalleOrdenService;
   
   @EJB
    private OrdenCompraFacadeLocal ordenOrdenService;
   
    @Getter
    @Setter
    private int idCompra;
    @Getter
    @Setter
    private OrdenCompra ordenCompraActiva;
     @Getter
    @Setter
    private List<DetalleOrdenCompra> listaDetalleOrden;
    /**
     * Creates a new instance of BackingBean
     */
    @PostConstruct
    public void init() {
        System.out.println("carga de impresion de productos ");
        
        //final String id = FacesUtils.getRequestParameter("idCompra");
        idCompra = productoIniciarCompraBean.getIdOrdenParam();
        System.out.println("ID recogido "+idCompra);
        cargarDetalleOrden(idCompra);
    }

    private void cargarDetalleOrden(int idOrden){
        
        this.ordenCompraActiva = ordenOrdenService.find(idOrden);
        
        this.listaDetalleOrden =  detalleOrdenService.findByOrden(idOrden);
                
    }

    /**
     * @return the productoIniciarCompraBean
     */
    public ProductoIniciarCompraBean getProductoIniciarCompraBean() {
        return productoIniciarCompraBean;
    }
    

}
