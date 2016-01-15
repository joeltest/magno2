/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;


import com.magno.magno.entity.Categoria;
import com.magno.servicios.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author ihsa
 */
@ManagedBean
@ViewScoped
public class MenuBean implements Serializable{
    @EJB
    private CategoriaFacadeLocal categoriaService;

    private List<Categoria> menuPrincipal;
    private List<Categoria> menuChild;
    /**
     * Creates a new instance of BackingBean
     */
    
    
    public List<Categoria> getMenuPrincipal(){
        this.menuPrincipal = categoriaService.findAllMenu();
        System.out.println(" ssss *********"+menuPrincipal.toString());
         return  menuPrincipal;
    }
    
    public List<Categoria> getMenuChild(int idCategoria){
         return categoriaService.findAllMenuChild(idCategoria);
    }

 
    public void setMenuPrincipal(List<Categoria> menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
    }

   
    public List<Categoria> getMenuChild() {
        return menuChild;
    }

    public void setMenuChild(List<Categoria> menuChild) {
        this.menuChild = menuChild;
    }
    
    
}
