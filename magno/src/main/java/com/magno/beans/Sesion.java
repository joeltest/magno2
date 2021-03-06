/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.beans.constantes.Constantes;
import com.magno.magno.entity.Cliente;
import com.magno.magno.entity.Producto;
import com.magno.magno.entity.Sucursal;
import com.magno.magno.entity.Usuario;
import com.magno.servicios.ClienteFacadeLocal;
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
    private ClienteFacadeLocal clienteFacadeLocal;

    @EJB
    private SucursalFacadeLocal sucursalServie;

    @EJB
    private ProductoFacadeLocal productoService;

    private Sucursal sucursalActiva;
    private Sucursal sucursalAdmin;
    private int sucursalSeleccionada;
    private int sucursalAdminSeleccionada;
    private Usuario usuarioSesion;
    private String usuarioTemp;
    private String claveTemp;

    private List<Producto> listaProductoSlider;
    private List<Producto> listaProductoSliderPrincipal;

    private List<SelectItem> listaSucursales;

    private Cliente clienteSesion;
    
    public Sesion() {

    }

    @PostConstruct
    public void init() {
//        usuarioSesion = usuarioFacadeLocal.find(1);
        this.sucursalActiva = sucursalServie.find(Constantes.ID_SUCURSAL_OMETEPEC);
        this.sucursalAdmin = sucursalServie.find(Constantes.ID_SUCURSAL_OMETEPEC);
        cargarSucursales();
        List<ProductoListaVo> listaProducto = productoService.findAllPrincipal();
        if (!listaProducto.isEmpty()) {
            this.listaProductoSlider = listaProducto.get(0).getListaProducto();
        }
        
        listaProductoSliderPrincipal = productoService.findAllProductos(sucursalActiva.getId(),62);
        
    }

    public void login() {
        System.out.println("usuario "+usuarioTemp);
        System.out.println("usuario "+claveTemp);
        System.out.println("usuario "+sucursalAdmin.getNombre());
//        sucursalAdmin =  sucursalServie.find(sucursalAdminSeleccionada);
        this.setUsuarioSesion(usuarioFacadeLocal.login(usuarioTemp, claveTemp, sucursalAdmin.getId()));
        if (getUsuarioSesion() == null) {
            MensajeUtils.addErrorMessage("Acceso denegado", "el usuario o la clave son incorrectos...");
        } else {
            MensajeUtils.addInfoMessage("Bienvenido", "...");
        }
    }
    
    public String loginCliente() {
        String re = "";
        this.clienteSesion = (clienteFacadeLocal.login(usuarioTemp, claveTemp));
        if (clienteSesion == null) {
            MensajeUtils.addErrorMessage("Acceso denegado", "el usuario o la clave son incorrectos...");
        } else {
            MensajeUtils.addInfoMessage("Bienvenido", "...");
            return "/principal.xhtml";
        }
        return re;
        
    }

    public void redireccionarPrincipal(ActionEvent event) {
        UtilsRedirect.redireccionarContexto("/magno");
    }

    public List<Producto> getListaProductoSlider() {
        return this.listaProductoSlider;
    }
    
    public List<Producto> getListaProductoSliderPrincipal() {
        return this.listaProductoSliderPrincipal;
    }

    public void cambiarSucursal(ValueChangeEvent change) {
        this.sucursalActiva = sucursalServie.find(change.getNewValue());
    }
    
    public void cambiarSucursalAdmin(ValueChangeEvent change) {
        sucursalAdminSeleccionada 
                = (Integer )change.getNewValue();
        this.sucursalAdmin = sucursalServie.find(sucursalAdmin);
    }

    public void cargarSucursales() {

        List<Sucursal> listaS = sucursalServie.findAll();
        this.listaSucursales = new ArrayList<>();
        for (Sucursal suc : listaS) {
            getListaSucursales().add(new SelectItem(suc.getId(), suc.getNombre()));
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
        this.claveTemp = "";
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

    public Cliente getClienteSesion() {
        return clienteSesion;
    }

    public void setClienteSesion(Cliente clienteSesion) {
        this.clienteSesion = clienteSesion;
    }

    public Sucursal getSucursalAdmin() {
        return sucursalAdmin;
    }

    public void setSucursalAdmin(Sucursal sucursalAdmin) {
        this.sucursalAdmin = sucursalAdmin;
    }

    public int getSucursalAdminSeleccionada() {
        return sucursalAdminSeleccionada;
    }

    public void setSucursalAdminSeleccionada(int sucursalAdminSeleccionada) {
        this.sucursalAdminSeleccionada = sucursalAdminSeleccionada;
    }

}
