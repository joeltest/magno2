/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.Categoria;
import com.magno.magno.entity.Cliente;
import com.magno.magno.entity.DetalleOrdenCompra;
import com.magno.magno.entity.OrdenCompra;
import com.magno.magno.entity.Producto;
import com.magno.magno.entity.Sucursal;
import com.magno.servicios.CategoriaFacadeLocal;
import com.magno.servicios.DetalleOrdenCompraFacadeLocal;
import com.magno.servicios.OrdenCompraFacadeLocal;
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
import com.magno.utils.FacesUtils;
import com.magno.utils.MensajeUtils;
import com.magno.utils.UtilsRedirect;
import com.magno.beans.constantes.Constantes;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import mail.SesionMail;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class ProductoIniciarCompraBean implements Serializable {

    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;

    @EJB
    private ProductoFacadeLocal productoService;

    @EJB
    private CategoriaFacadeLocal categoriaService;

    @EJB
    private OrdenCompraFacadeLocal ordenService;
    
    @EJB
    private DetalleOrdenCompraFacadeLocal detalleOrdenService;

    @EJB
    private SucursalFacadeLocal sucursalService;

    @Getter
    @Setter
    private Producto productoSeleccionado;

    @Getter
    @Setter
    private ProductoVo productoVoSeleccionado;

    private List<Producto> listaProductos;
    private List<ProductoVo> listaProductosCarrito;
     @Getter
    @Setter
    private List<SelectItem> listaSucursalItems = null;
    @Getter
    @Setter
    private List<DetalleOrdenCompra> listaDetalleOrden;
    @Getter
    @Setter
    private int idSeleccionado;
    @Getter
    @Setter
    private int idSucursalSeleccionada;
    @Getter
    @Setter
    private Cliente cliente;
    @Getter
    @Setter
    private int cantidadNueva;
    @Getter
    @Setter
    private int idOrdenParam;

    @Getter
    @Setter
    private double importeTotal;
    @Getter
    @Setter
    private int cantidadTotal;
    @Getter
    @Setter
    private OrdenCompra ordenCompraActiva;

    @Getter
    @Setter
    private OrdenCompra ordenCompraImprimir;

    @Getter
    @Setter
    private Constantes.CrudActions OPERACION;

    private Categoria categoriaActiva;

    
    /**
     * Creates a new instance of BackingBean
     */
//    @PostConstruct
//    public void init() {
//        System.out.println("carga de productos ");
//    }
    public void cargarProductosPorCategoria(ActionEvent event) {
        String idCategoria = FacesUtils.getRequestParameter("idCategoria");
        System.out.println("Categoria param " + idCategoria);
        int idCat = Integer.valueOf(idCategoria);
        setCategoriaActiva(categoriaService.find(idCat));
        System.out.println("Cate activa" + getCategoriaActiva().toString());
        cargarLista(idCat);
    }

    private void cargarLista(int idCategoria) {
        System.out.println("el id de categoria" + idCategoria);
        //this.setListaProductos(productoService.findAllProductos(sesion.getUsuarioSesion().getSucursalId().getId(),idCategoria));
        this.setListaProductos(productoService.findAllProductos(1, idCategoria));
    }

    public void agregarProductoACarrito(ActionEvent event) {
        if (listaProductosCarrito == null) {
            listaProductosCarrito = new ArrayList<>();
        }

        String param = FacesUtils.getRequestParameter("idProducto");
        int paramInt = Integer.parseInt(param);
        final ProductoVo voEncotrado = buscarProductoCarrit(paramInt);
        
        if (voEncotrado != null) {
            voEncotrado.setCantidad(voEncotrado.getCantidad()+1);
        } else {
            final ProductoVo productoVo = new ProductoVo();
            productoVo.setCantidad(1);
            productoVo.setProducto(productoService.find(paramInt));
            listaProductosCarrito.add(productoVo);
        }
        calcularTotales();
    }

    public void verDetalleProducto(ActionEvent event) {
        String param = FacesUtils.getRequestParameter("idProducto");
        int paramInt = Integer.parseInt(param);
        productoSeleccionado = productoService.find(paramInt);
    }

    public void calcularTotales() {
        double total = 0;
        int cantidadTotal = 0;
        for (ProductoVo vo : listaProductosCarrito) {
            double subTotal = vo.getProducto().getPrecio() * vo.getCantidad();
            vo.setSubTotal(subTotal);
            total += vo.getSubTotal();
            cantidadTotal += vo.getCantidad();
        }
        this.importeTotal = total;
        this.cantidadTotal = cantidadTotal;
        //cantidad

    }

    public void quitarCarrito(ActionEvent event) {
        String idParam = FacesUtils.getRequestParameter("idProducto");
        int id = Integer.parseInt(idParam);
        final ProductoVo vo = buscarProductoCarrit(id);
        listaProductosCarrito.remove(vo);
        calcularTotales();
        MensajeUtils.addInfoMessage("Producto eliminado", "Se eiminó el producto del carrito de compras...");
    }

    private ProductoVo buscarProductoCarrit(int idProducto) {
        ProductoVo voEncontrado = null;
        for (ProductoVo vo : listaProductosCarrito) {
            if (vo.getProducto().getId() == idProducto) {
                voEncontrado = vo;
                break;
            }
        }
        return voEncontrado;
    }

    public void cambiarCantidad(ActionEvent event) {
        calcularTotales();
        MensajeUtils.addInfoMessage("Cantidad cambiada", "Se cambió la cantidad ...");
    }

    public void iniciarCrearOrden(ActionEvent event) {
        this.listaDetalleOrden = null;
        this.ordenCompraActiva = null;
        llenarSucursalItem();
        cliente = new Cliente();
    }

    private void limpiarTodo(){
        listaProductosCarrito.clear();
        this.cantidadTotal=0;
        this.categoriaActiva = null;
        this.importeTotal = 0;
        
    }
    
    public void crearOrden(ActionEvent event) {
        System.out.println("Cleinte "+cliente.toString());
        ordenCompraImprimir = ordenService.guardarOrden(listaProductosCarrito, importeTotal, cliente,idSucursalSeleccionada);        
        limpiarTodo();               
        //sending email
        
        StringBuilder correoBody = new StringBuilder();
        correoBody.append("<br/>");
        correoBody.append("Usted a realizado una orden de compra en el sistema Magno Express Web");
        correoBody.append("<br/>");
        correoBody.append("Codigo de orden :").append(ordenCompraImprimir.getCodigo());
        correoBody.append("<br/>");
        correoBody.append("Sucursal :").append(ordenCompraImprimir.getSucursalId().getNombre());
        correoBody.append("<br/>");
        correoBody.append("Cliente :").append(ordenCompraImprimir.getClienteId().getNombre());
        correoBody.append("<br/>");
        correoBody.append("Correo :").append(ordenCompraImprimir.getClienteId().getCorreo());
        correoBody.append("<br/>");
        correoBody.append("Tel :").append(ordenCompraImprimir.getClienteId().getTelefono());
        correoBody.append("<br/>");
        correoBody.append("<br/>");
        correoBody.append("Gracias por su compra");
        
        enviarCorreo(cliente.getCorreo(), "velocirraptor79@hotmail.com", "ORDEN :"+ordenCompraImprimir.getCodigo(), correoBody);
        MensajeUtils.addInfoMessage("Orden Enviada", "Se envio la orden a la sucursal no olvide imprimir su listado..");
        cargarDetalleOrden();
    }

    
    private void cargarDetalleOrden(){
        this.listaDetalleOrden =  detalleOrdenService.findByOrden(ordenCompraImprimir.getId());
                
    }
    
    
    public void redireccionMostrarProductos(ActionEvent event) {
        UtilsRedirect.redireccionarContexto("magno/faces/mostrarProductos.xhtml");
    }
    
   public void llenarSucursalItem() {

        List<Sucursal> listaSucursales = sucursalService.findAll();

        if (listaSucursales != null && !listaSucursales.isEmpty()) {

            listaSucursalItems = new ArrayList<>();

            for (Sucursal sucu : listaSucursales) {

                SelectItem item = new SelectItem();

                item.setValue(sucu.getId());

                item.setLabel(sucu.getNombre());

                listaSucursalItems.add(item);
            }
        }
    }
    
     public void valueChangeSucursal(ValueChangeEvent v) {
        this.idSucursalSeleccionada = (Integer) v.getNewValue();
    }
    
       private void enviarCorreo(String para, String Cc, String asunto, StringBuilder cuerpo) {
        SesionMail sesionMail = new SesionMail();
        sesionMail.setPara(para);
        sesionMail.setAsunto(asunto);
        sesionMail.setCc(Cc);
        sesionMail.setCuerpo(cuerpo.toString());
        try {
            sesionMail.enviarMensaje();
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Excepcion al enviar correo " + ex.getMessage());
        }
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

    /**
     * @return the listaProductos
     */
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    /**
     * @param listaProductos the listaProductos to set
     */
    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    /**
     * @return the categoriaActiva
     */
    public Categoria getCategoriaActiva() {
        return categoriaActiva;
    }

    /**
     * @param categoriaActiva the categoriaActiva to set
     */
    public void setCategoriaActiva(Categoria categoriaActiva) {
        this.categoriaActiva = categoriaActiva;
    }

    /**
     * @return the listaProductosCarrito
     */
    public List<ProductoVo> getListaProductosCarrito() {
        return listaProductosCarrito;
    }

    /**
     * @param listaProductosCarrito the listaProductosCarrito to set
     */
    public void setListaProductosCarrito(List<ProductoVo> listaProductosCarrito) {
        this.listaProductosCarrito = listaProductosCarrito;
    }

}
