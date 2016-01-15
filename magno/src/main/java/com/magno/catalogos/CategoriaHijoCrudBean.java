/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.magno.entity.Categoria;
import com.magno.magno.entity.Marca;
import com.magno.magno.entity.Sucursal;
import com.magno.servicios.CategoriaFacadeLocal;
import com.magno.servicios.FacadeLocal;
import com.magno.servicios.MarcaFacadeLocal;
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
public class CategoriaHijoCrudBean extends BaseCrud<Categoria> {

    @EJB
    CategoriaFacadeLocal servicio;

    @EJB
    SucursalFacadeLocal sucursalServicio;

    @Getter
    @Setter
    private List<SelectItem> listaCategoriasHijoItems = null;

    @Getter
    @Setter
    private int idPadreSeleccionado;

    public CategoriaHijoCrudBean() {
        super(Categoria.class);
    }

    @PostConstruct
    public void init() {
        try {
            preprarNuevoRegistro();
            llenarPadresItem();
        } catch (InstantiationException ex) {
            Logger.getLogger(CategoriaHijoCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CategoriaHijoCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void antesGuardar(ActionEvent e) {
        getSelected().setNivel(2);
        getSelected().setEliminado("False");
        getSelected().setCategoriaPadre(new Categoria(idPadreSeleccionado));
        guardar(e);
        llenarPadresItem();
    }

    public void iniciarModificacion(ActionEvent e){
        prepararModificacion();
        
        idPadreSeleccionado = getSelected().getCategoriaPadre().getId();
        
        
    }
    
    public void antesModificar(ActionEvent e) {
        getSelected().setNivel(2);
        getSelected().setEliminado("False");
        getSelected().setCategoriaPadre(new Categoria(idPadreSeleccionado));
        modificar(e);
        llenarPadresItem();
    }
    
    public List<Categoria> getCategoriasPadres() {
        return this.servicio.findAllMenu();
    }

    public List<Categoria> getCategoriasHijo() {
        return this.servicio.findAllMenuChild(idPadreSeleccionado);
    }

    public void eliminacion(ActionEvent event) {
        prepararEliminacion(event);
        getSelected().setEliminado("True");
        persistir();
    }

    public void llenarPadresItem() {

        List<Categoria> listaSuc = servicio.findAllMenu();

        if (listaSuc != null && !listaSuc.isEmpty()) {

            listaCategoriasHijoItems = new ArrayList<>();

            for (Categoria suc : listaSuc) {

                SelectItem item = new SelectItem();

                item.setValue(suc.getId());

                item.setLabel(suc.getNombre());

                listaCategoriasHijoItems.add(item);
            }
        }
    }

    public void valueChangeCategoriaPadre(ValueChangeEvent v) {
        this.idPadreSeleccionado = (Integer) v.getNewValue();
    }

    @Override
    protected FacadeLocal<Categoria> getService() {
        return servicio;
    }

}
