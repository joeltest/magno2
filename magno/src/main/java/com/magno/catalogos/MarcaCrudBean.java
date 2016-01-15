/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.magno.entity.Marca;
import com.magno.magno.entity.Oferta;
import com.magno.magno.entity.Sucursal;
import com.magno.servicios.FacadeLocal;
import com.magno.servicios.MarcaFacadeLocal;
import com.magno.servicios.OfertaFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
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
public class MarcaCrudBean extends BaseCrud<Marca> {

    @EJB
    MarcaFacadeLocal servicio;
    
    @EJB
    SucursalFacadeLocal sucursalServicio;
    
     @Getter
    @Setter
    private List<SelectItem> listaSucursalItems = null;
     
     @Getter
    @Setter
     private int idSucursalSeleccionado;

    public MarcaCrudBean() {
        super(Marca.class);
    }

    @PostConstruct
    public void init() {
        try {
            preprarNuevoRegistro();
        } catch (InstantiationException ex) {
            Logger.getLogger(MarcaCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MarcaCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    public void eliminacion(ActionEvent event){   
        prepararEliminacion(event);
        getSelected().setEliminado("True");
        persistir();
    }

    @Override
    protected FacadeLocal<Marca> getService() {
        return servicio;
    }
    
    

}
