/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.servicios.OrdenCompraFacadeLocal;
import com.magno.servicios.VentaFacadeLocal;
import com.magno.vo.VentaVo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jorodriguez
 */
//@Named(value = "consultaVentas")
@ManagedBean
@SessionScoped
public class ConsultaVentas implements Serializable{
    
    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    
    @EJB
    private VentaFacadeLocal ventaServicio;
   
    @Getter
    @Setter
    private List<VentaVo> listaVentaVo;
    
    @Getter
    @Setter
    private Date fechaInicio;
   
    @Getter
    @Setter
    private Date fechaFin;

    /**
     * Creates a new instance of ConsultaVentas
     */
    public ConsultaVentas() {
    }
    
    public void buscarVentas(ActionEvent event){
        System.out.println("Buscar ");
            listaVentaVo = ventaServicio.findAll(sesion.getUsuarioSesion().getSucursalId().getId(), fechaInicio, fechaFin);
    }

    /**
     * @return the sesion
     */
    public Sesion getSesion() {
        return sesion;
    }

    /**
     * @param sesion the sesion to set
     */
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

       
}
