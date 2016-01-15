/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.Usuario;
import com.magno.servicios.UsuarioFacadeLocal;
import static com.magno.utils.FacesUtils.getRequestParameter;
import com.magno.utils.MensajeUtils;
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
public class UsuarioBean implements Serializable {

    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    @EJB
    private UsuarioFacadeLocal service;
    @Getter
    @Setter
    private Usuario usuario;
    
    @Getter
    @Setter
    private List<Usuario> lista;
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
        cargar();
    }

    public void prepararNuevo(ActionEvent event) {
        usuario = new Usuario();
        OPERACION = Constantes.CrudActions.INSERTAR;
        limpiar();
    }

    private void limpiar() {
        this.idSeleccionado = -1;
    }

    public void guardar(ActionEvent event) {
        
        //crear obj
        service.create(usuario);
        cargar();
        MensajeUtils.addInfoMessage("Se guardó", "Se realizó el cambio existosamente...");
    }

    public void prepararModificacion(ActionEvent event) {
        seleccionarRegistro();        
        OPERACION = Constantes.CrudActions.MODIFICAR;
    }

    public void modificar(ActionEvent event) {
        
            //recojer objetos
        service.edit(usuario);        
        cargar();
        MensajeUtils.addInfoMessage("Se modificó", "Se realizó el cambio existosamente...");
    }
    
    public void prepararEliminacion(ActionEvent event) {
        seleccionarRegistro();
        OPERACION = Constantes.CrudActions.ELIMINAR;
    }

    public void eliminar(ActionEvent event) {
        usuario.setEliminado(Constantes.ELIMINADO_TRUE);
        service.edit(usuario);
        cargar();
        MensajeUtils.addInfoMessage("Se eliminó", "Se realizó el cambio existosamente...");
    }

    private void seleccionarRegistro() {
        idSeleccionado = Integer.parseInt(getRequestParameter("param"));
        if (idSeleccionado != 0) {
            usuario = service.find(idSeleccionado);
        }

    }

    public void cargar() {
        lista = service.findAllEntity();
    }

//    public void valueChangeCategoriasPadre(ValueChangeEvent v) {
//        this.idCategoriaPadreSeleccionada = (Integer) v.getNewValue();
//        llenarCategoriasHijoItem(idCategoriaPadreSeleccionada);
//    }

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
