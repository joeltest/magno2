/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.DetalleOrdenCompra;
import com.magno.magno.entity.Estatus;
import com.magno.magno.entity.OrdenCompra;
import com.magno.magno.entity.Venta;
import com.magno.servicios.DetalleOrdenCompraFacadeLocal;
import com.magno.servicios.OrdenCompraFacadeLocal;
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.servicios.VentaFacadeLocal;
import com.magno.utils.FacesUtils;
import com.magno.beans.constantes.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class SurtirOrdenBean implements Serializable {

//    @ManagedProperty(value = "#{sesion}")
//    private Sesion sesion;
    @EJB
    private OrdenCompraFacadeLocal ordenService;

    @EJB
    private DetalleOrdenCompraFacadeLocal detalleOrdenService;

    @EJB
    private ProductoFacadeLocal productoService;
    @EJB
    private VentaFacadeLocal ventaService;

    @Getter
    @Setter
    private List<OrdenCompra> listaOrdenes;
    @Getter
    @Setter
    private List<DetalleOrdenCompra> listaDetalleOrden;

    @Getter
    @Setter
    private OrdenCompra ordenSeleccionada;
    @Getter
    @Setter
    private Venta ventaSeleccionada;

    @PostConstruct
    public void init() {
        System.out.println("carga de ordenes en estatus pendiente ");
        String param = FacesUtils.getRequestParameter("idCompra");
        if (param != null || !param.isEmpty()) {
            obtenerOrden(Integer.parseInt(param));
        }
    }

    public void cargarOrden(ActionEvent event) {
        String param = FacesUtils.getRequestParameter("idCompra");
        if (param != null || !param.isEmpty()) {
            obtenerOrden(Integer.parseInt(param));
        }
    }
    

    public void obtenerOrden(int idOrden) {
        System.out.println("Idcompra desde surtir " + idOrden);
        this.ordenSeleccionada = ordenService.find((idOrden));
        //buscar venta de esa orden        
        cargarDetalleOrden(ordenSeleccionada.getId());

        ventaSeleccionada = ventaService.find(ordenSeleccionada.getId());

    }

    private void cargar() {
        this.listaOrdenes = ordenService.findAll(Constantes.ESTATUS_ENVIADA);
    }

    private void cargarDetalleOrden(int idOrden) {
        this.listaDetalleOrden = detalleOrdenService.findByOrden(idOrden);
    }

    public void realizarVenta(ActionEvent event) {
        for (DetalleOrdenCompra detalle : listaDetalleOrden) {

            detalle.setVenta(ventaSeleccionada);
            detalleOrdenService.edit(detalle);
        }
        ordenSeleccionada.setEstatusId(new Estatus(Constantes.ESTATUS_SURTIDA));
        ordenService.edit(ordenSeleccionada);
    }

    public void surtido(ActionEvent event) {
        String param = FacesUtils.getRequestParameter("idDetalle");

        DetalleOrdenCompra detalle = detalleOrdenService.find(Integer.parseInt(param));

        detalle.setVenta(ventaSeleccionada);

        detalleOrdenService.edit(detalle);

    }

    public void noSurtido(ActionEvent event) {
        String param = FacesUtils.getRequestParameter("idDetalle");

        DetalleOrdenCompra detalle = detalleOrdenService.find(Integer.parseInt(param));

        detalle.setCancelado(Constantes.BOOLEAN_TRUE);

        detalleOrdenService.edit(detalle);

    }

//    public void verDetalle(ActionEvent event){
//        
//        String param = FacesUtils.getRequestParameter("idOrden");
//        System.out.println("Param "+param);
//        this.ordenSeleccionada = ordenService.find(Integer.parseInt(param));
//        
//        cargarDetalleOrden(ordenSeleccionada.getId());        
//        
//    }
}
