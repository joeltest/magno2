/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.Producto;
import com.magno.magno.entity.Usuario;
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.servicios.UsuarioFacadeLocal;
import static com.magno.utils.FacesUtils.getRequestParameter;
import com.magno.utils.MensajeUtils;
import com.magno.vo.ProductoListaVo;
import com.magno.beans.constantes.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@ViewScoped

public class Consultas implements Serializable {

    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    @EJB
    private ProductoFacadeLocal productoService;
    @Getter
    @Setter
    private Usuario usuario;
    
    @Getter
    @Setter
    private List<Usuario> lista;
    @Getter
    @Setter
    private List<ProductoListaVo> listaPrincipalProducto;
    @Getter
    @Setter
    private int idSeleccionado;
  
    @Getter
    @Setter
    private List<SelectItem> listaCategoriaPadreItems = null;
    
    @Getter
    @Setter
    private Constantes.CrudActions OPERACION;



    @PostConstruct
    public void init() {
        System.out.println("carga de usuaruio ");
        cargarProductosPrincipal();
    }


    private void cargarOfertas(){
        
    }
    
    private void cargarProductosPrincipal(){
        listaPrincipalProducto = productoService.findAllPrincipal();
    }
    
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
