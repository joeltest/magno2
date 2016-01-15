/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.beans.constantes.Constantes;
import com.magno.magno.entity.Producto;
import com.magno.magno.entity.Sucursal;
import com.magno.magno.entity.Usuario;
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
import com.magno.servicios.UsuarioFacadeLocal;
import com.magno.utils.MensajeUtils;
import com.magno.utils.UtilsRedirect;
import com.magno.vo.ProductoListaVo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author jorodriguez
 */
@Named(value = "sesion")
@SessionScoped
public class Sesion implements Serializable {
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    
    @EJB
    private SucursalFacadeLocal  sucursalServie;
    
    @EJB private ProductoFacadeLocal productoService;
           
    private Sucursal sucursalActiva;
    private int sucursalSeleccionada;
    private Usuario usuarioSesion;
    private String usuarioTemp;
    private String claveTemp;
    
    private List<Producto> listaProductoSlider;
    
    private List<SelectItem> listaSucursales;
    
    
    public Sesion() {
        
    }
    @PostConstruct
    public void init(){
//        usuarioSesion = usuarioFacadeLocal.find(1);
         this.sucursalActiva = sucursalServie.find(Constantes.ID_SUCURSAL_OMETEPEC);
         cargarSucursales();
    }
    
    public void login(){
        this.setUsuarioSesion(usuarioFacadeLocal.login(usuarioTemp, claveTemp, 1));
        if(getUsuarioSesion() == null){
              MensajeUtils.addErrorMessage("Acceso denegado", "el usuario o la clave son incorrectos...");
        }else{
              MensajeUtils.addInfoMessage("Bienvenido", "...");
        }
    }
    
   public void redireccionarPrincipal(ActionEvent event){
        UtilsRedirect.redireccionarContexto("/magno");
   }
   
    public List<Producto> getListaProductoSlider() {
          List<ProductoListaVo> listaProducto = productoService.findAllPrincipal();
          if(!listaProducto.isEmpty()){
                this.listaProductoSlider = listaProducto.get(0).getListaProducto();
          }
        return this.listaProductoSlider;
    }

    
    public void cambiarSucursal(ValueChangeEvent change){
        this.sucursalActiva = sucursalServie.find(change.getNewValue());
    }

    public void cargarSucursales(){
        
        List<Sucursal> listaS = sucursalServie.findAll();
        this.listaSucursales  = new ArrayList<>();
        for(Sucursal  suc : listaS){
            getListaSucursales().add(new SelectItem(suc.getId(),suc.getNombre()));
        }      
        
    }
    
    /**
     * @return the usuarioSesion
     */
    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    /**
     * @param usuarioSesion the usuarioSesion to set
     */
    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
     public void cerrarSesion(ActionEvent event) {
        this.setUsuarioSesion(null);
        this.claveTemp="";
        this.usuarioTemp = "";
//        Util.redireccionarContexto("/Restaurant-web");
    }

    /**
     * @return the usuarioTemp
     */
    public String getUsuarioTemp() {
        return usuarioTemp;
    }

    /**
     * @param usuarioTemp the usuarioTemp to set
     */
    public void setUsuarioTemp(String usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

    /**
     * @return the claveTemp
     */
    public String getClaveTemp() {
        return claveTemp;
    }

    /**
     * @param claveTemp the claveTemp to set
     */
    public void setClaveTemp(String claveTemp) {
        this.claveTemp = claveTemp;
    }

    public Sucursal getSucursalActiva() {
        return sucursalActiva;
    }

    public void setSucursalActiva(Sucursal sucursalActiva) {
        this.sucursalActiva = sucursalActiva;
    }

    public int getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(int sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public List<SelectItem> getListaSucursales() {
        return listaSucursales;
    }


}
