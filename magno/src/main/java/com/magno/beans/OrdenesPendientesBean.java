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
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.utils.FacesUtils;
import com.magno.beans.constantes.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@ViewScoped
public class OrdenesPendientesBean implements Serializable {

//    @ManagedProperty(value = "#{sesion}")
//    private Sesion sesion;
    
    @EJB
    private OrdenCompraFacadeLocal ordenService;
    
    @EJB
    private DetalleOrdenCompraFacadeLocal detalleOrdenService;

    @EJB
    private ProductoFacadeLocal productoService;
    
    @Getter
    @Setter
    private List<OrdenCompra> listaOrdenes;
    @Getter
    @Setter
    private List<DetalleOrdenCompra> listaDetalleOrden;
    
    @Getter
    @Setter
    private OrdenCompra ordenSeleccionada;

    @PostConstruct
    public void init() {
        System.out.println("carga de ordenes en estatus pendiente ");
        cargar();
    }

    private void cargar(){
        this.listaOrdenes = ordenService.findAll(Constantes.ESTATUS_ENVIADA);
    }
    
    private void cargarDetalleOrden(int idOrden){
        this.listaDetalleOrden = detalleOrdenService.findByOrden(idOrden);
    }

    
    public void verDetalle(ActionEvent event){
        
        String param = FacesUtils.getRequestParameter("idOrden");
        System.out.println("Param "+param);
        this.ordenSeleccionada = ordenService.find(Integer.parseInt(param));
        
        cargarDetalleOrden(ordenSeleccionada.getId());        
        
    }
    

}
