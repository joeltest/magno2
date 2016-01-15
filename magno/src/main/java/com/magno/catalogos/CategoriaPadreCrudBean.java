/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.magno.entity.Categoria;
import com.magno.magno.entity.Marca;
import com.magno.servicios.CategoriaFacadeLocal;
import com.magno.servicios.FacadeLocal;
import com.magno.servicios.MarcaFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class CategoriaPadreCrudBean extends BaseCrud<Categoria> {

    @EJB
    CategoriaFacadeLocal servicio;
    
    @EJB
    SucursalFacadeLocal sucursalServicio;
    
     @Getter
    @Setter
    private List<SelectItem> listaSucursalItems = null;
     
     @Getter
    @Setter
     private int idSucursalSeleccionado;

    public CategoriaPadreCrudBean() {
        super(Categoria.class);
    }

    @PostConstruct
    public void init() {
        try {
            preprarNuevoRegistro();
        } catch (InstantiationException ex) {
            Logger.getLogger(CategoriaPadreCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CategoriaPadreCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void antesGuardar(ActionEvent e){
        getSelected().setNivel(1);
        getSelected().setCategoriaPadre(null);
        getSelected().setEliminado("False");
        guardar(e);
    }
    
    public List<Categoria> getCategoriasPadres(){    
            return this.servicio.findAllMenu();
    }
    
    
    public void eliminacion(ActionEvent event){   
        prepararEliminacion(event);
        getSelected().setEliminado("True");
        persistir();
        
    }

    @Override
    protected FacadeLocal<Categoria> getService() {
        return servicio;
    }
    
    

}
