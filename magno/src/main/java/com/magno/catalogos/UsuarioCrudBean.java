/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.magno.entity.Oferta;
import com.magno.magno.entity.Rol;
import com.magno.magno.entity.Sucursal;
import com.magno.magno.entity.Usuario;
import com.magno.servicios.FacadeLocal;
import com.magno.servicios.OfertaFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
import com.magno.servicios.UsuarioFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class UsuarioCrudBean extends BaseCrud<Usuario> {

    @EJB
    UsuarioFacadeLocal servicio;
    @EJB
    SucursalFacadeLocal sucursalServicio;
    
     @Getter
    @Setter
    private List<SelectItem> listaSucursalItems = null;
     
     @Getter
    @Setter
     private int idSucursalSeleccionado;

    public UsuarioCrudBean() {
        super(Usuario.class);
    }

    @PostConstruct
    public void init() {
        try {
            preprarNuevoRegistro();
            llenarSucursalItem();
        } catch (InstantiationException ex) {
            Logger.getLogger(UsuarioCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UsuarioCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void antesGuardar(ActionEvent e) throws InstantiationException, IllegalAccessException{
        
        getSelected().setSucursalId(new Sucursal(idSucursalSeleccionado));
        
        getSelected().setRolId(new Rol(1));
                
        guardar(e);
        
        preprarNuevoRegistro();
    }
    
    public void antesModificar(ActionEvent e) throws InstantiationException, IllegalAccessException{
        
        getSelected().setSucursalId(new Sucursal(idSucursalSeleccionado));
        
        modificar(e);
        
        preprarNuevoRegistro();
    }
    
    
    
    public void iniciarModificacion(ActionEvent e){
    
        idSucursalSeleccionado = getSelected().getSucursalId().getId();
        
        prepararModificacion();
        
    }
    
    
    public void eliminacion(ActionEvent event){   
        prepararEliminacion(event);
        getSelected().setEliminado("True");
        persistir();
    }
    
    
     public void llenarSucursalItem() {

        List<Sucursal> listaSuc = sucursalServicio.findAll();

        if (listaSuc != null && !listaSuc.isEmpty()) {

            listaSucursalItems = new ArrayList<>();

            for (Sucursal suc : listaSuc) {

                SelectItem item = new SelectItem();

                item.setValue(suc.getId());

                item.setLabel(suc.getNombre());

                listaSucursalItems.add(item);
            }
        }
    }
    public void valueChangeSucursal(ValueChangeEvent v) {
        this.idSucursalSeleccionado = (Integer) v.getNewValue();
    }
    @Override
    protected FacadeLocal<Usuario> getService() {
        return servicio;
    }

}
