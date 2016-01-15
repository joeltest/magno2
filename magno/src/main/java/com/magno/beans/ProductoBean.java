/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.magno.entity.Categoria;
import com.magno.magno.entity.Marca;
import com.magno.magno.entity.Producto;
import com.magno.magno.entity.SiArchivo;
import com.magno.servicios.CategoriaFacadeLocal;
import com.magno.servicios.MarcaFacadeLocal;
import com.magno.servicios.ProductoFacadeLocal;
import com.magno.servicios.SiArchivoFacadeLocal;
import static com.magno.utils.FacesUtils.getRequestParameter;
import com.magno.utils.MensajeUtils;
import com.magno.beans.constantes.Constantes;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ihsa
 */
@ManagedBean
@ViewScoped
public class ProductoBean implements Serializable {

    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;

    @EJB
    private ProductoFacadeLocal productoService;
    @EJB
    private SiArchivoFacadeLocal archivoService;
    @EJB
    private MarcaFacadeLocal marcaService;
    @EJB
    private CategoriaFacadeLocal categoriaService;
    @Getter
    @Setter
    private Producto producto;
    private List<Producto> listaProductos;
    @Getter
    @Setter
    private int idSeleccionado;
    @Getter
    @Setter
    private int idMarcaSelecciona;
    @Getter
    @Setter
    private int idCategoriaPadreSeleccionada;
    @Getter
    @Setter
    private int idCategoriaHijoSeleccionada;
    @Getter
    @Setter
    private List<SelectItem> listaCategoriaPadreItems = null;
    @Getter
    @Setter
    private List<SelectItem> listaCategoriaHijosItems = null;
    @Getter
    @Setter
    private List<SelectItem> listaMarcaItems = null;
    @Getter
    @Setter
    private Constantes.CrudActions OPERACION;
    @Getter
    @Setter
    private SiArchivo siArchivo;

    @Setter
    private StreamedContent image;
    /**
     * Creates a new instance of BackingBean
     */
    @PostConstruct
    public void init() {
        System.out.println("carga de productos ");
        cargar();
    }

    public void prepararNuevo(ActionEvent event) {
        producto = new Producto();
        siArchivo = new SiArchivo();
        OPERACION = Constantes.CrudActions.INSERTAR;
        cargarCombos();
        limpiar();
    }

    private void limpiar() {
        this.idCategoriaHijoSeleccionada = -1;
        this.idCategoriaPadreSeleccionada = -1;
        this.idMarcaSelecciona = -1;
    }

    public void guardar(ActionEvent event) {
        System.out.println("Guardar......" + OPERACION.toString());
        producto.setMarcaId(marcaService.find(this.idMarcaSelecciona));
        producto.setCategoriaId(categoriaService.find(this.idCategoriaHijoSeleccionada));
        producto.setUsuarioId(sesion.getUsuarioSesion());
        //FIXME : poner la sucursal en sesion y en un combo para realizar los cambio de sucursales
        producto.setSucursalId(sesion.getUsuarioSesion().getSucursalId());
        producto.setEliminado(Constantes.BOOLEAN_FALSE);
        producto.setAgotado(Constantes.BOOLEAN_FALSE);
        //guardar archivo
        archivoService.create(siArchivo);
        producto.setSiArchivo(siArchivo);
        productoService.create(producto);
        System.out.println(" @@@ producto " + producto.toString());
        cargar();
        MensajeUtils.addInfoMessage("Se guardó", "Se realizó el cambio existosamente...");
    }

    public void prepararModificacion(ActionEvent event) {
        cargarCombos();        
        seleccionarRegistro();        
        OPERACION = Constantes.CrudActions.MODIFICAR;
    }

    public void modificar(ActionEvent event) {
        System.out.println("Modificacion");
        System.out.println(" producto  " + producto.toString());
        archivoService.edit(siArchivo);
        producto.setSiArchivo(siArchivo);
        productoService.edit(producto);        
        cargar();
        MensajeUtils.addInfoMessage("Se modificó", "Se realizó el cambio existosamente...");
    }

    
    public void modificarArchivo(ActionEvent event){
        //modificar solo el archivo y asignarlo al producto y volver a hacer edit
        SiArchivo archivo = archivoService.find(producto.getSiArchivo());
        
        
    }
    
    public void prepararEliminacion(ActionEvent event) {
        cargarCombos();
        seleccionarRegistro();
        OPERACION = Constantes.CrudActions.ELIMINAR;
    }

    public void eliminar(ActionEvent event) {
        producto.setEliminado(Constantes.ELIMINADO_TRUE);
        productoService.edit(producto);
        cargar();
        MensajeUtils.addInfoMessage("Se eliminó", "Se realizó el cambio existosamente...");
    }

    private void seleccionarRegistro() {
        idSeleccionado = Integer.parseInt(getRequestParameter("param"));
        if (idSeleccionado != 0) {
            producto = productoService.find(idSeleccionado);
            this.siArchivo = producto.getSiArchivo();
            this.idCategoriaPadreSeleccionada = producto.getCategoriaId().getCategoriaPadre().getId();
            this.idCategoriaHijoSeleccionada = producto.getCategoriaId().getId();
            this.idMarcaSelecciona = producto.getMarcaId().getId();
        }

    }

    public void cargar() {
        listaProductos = productoService.findAllProductos(sesion.getUsuarioSesion().getSucursalId().getId());
    }
    
    public void cargarMasVendidos() {
        listaProductos = productoService.findAllToday();
    }

    public void uploadAttachment(FileUploadEvent event) {
        UploadedFile file = event.getFile();
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        siArchivo.setArchivo(file.getContents());
        siArchivo.setNombre(file.getFileName());
        siArchivo.setTipoArchivo(file.getContentType());
        siArchivo.setEliminado(Constantes.ELIMINADO_FALSE);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("Archivo cargado: %s ", file.getFileName()),
                ""));

    }

    public StreamedContent getImagen(SiArchivo siArchivo) {
        StreamedContent result = null;
//            if (siArchivo != null) {
//                InputStream input = new ByteArrayInputStream(siArchivo.getArchivo());
//                result  = new DefaultStreamedContent(input, siArchivo.getTipoArchivo());
//                return result;
//            } else {
//                return null;
//            }
        if (siArchivo != null) {
            InputStream is = new ByteArrayInputStream(siArchivo.getArchivo());
            image = new DefaultStreamedContent(is, siArchivo.getTipoArchivo());
            //result = new DefaultStreamedContent(new ByteArrayInputStream(), siArchivo.getTipoArchivo());
        }
        return result;
    }

    public StreamedContent getBytesToStreamedContent(SiArchivo archivo) {
        //String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item_id");
        StreamedContent result = null;
        if (archivo != null) {
//            System.out.println("Arxchivo "+archivo.toString());
//            SiArchivo siArchivo = archivoService.find(Integer.valueOf(id));
            InputStream is = new ByteArrayInputStream(archivo.getArchivo());
            result = new DefaultStreamedContent(is);
            //result = new DefaultStreamedContent(new ByteArrayInputStream(), siArchivo.getTipoArchivo());
        }
        return result;
    }

    private void cargarCombos() {
        llenarMarcasItem();
        llenarCategoriasPadreItem();
    }

    public void llenarMarcasItem() {

        List<Marca> listaMarca = marcaService.findAllMarca();

        if (listaMarca != null && !listaMarca.isEmpty()) {

            listaMarcaItems = new ArrayList<>();

            for (Marca marca : listaMarca) {

                SelectItem item = new SelectItem();

                item.setValue(marca.getId());

                item.setLabel(marca.getNombre());

                listaMarcaItems.add(item);
            }
        }
    }

    public void llenarCategoriasPadreItem() {

        List<Categoria> listaCategoriaPadre = categoriaService.findAllMenu();

        if (listaCategoriaPadre != null && !listaCategoriaPadre.isEmpty()) {

            listaCategoriaPadreItems = new ArrayList<>();

            for (Categoria cat : listaCategoriaPadre) {

                SelectItem item = new SelectItem();

                item.setValue(cat.getId());

                item.setLabel(cat.getNombre());

                listaCategoriaPadreItems.add(item);
            }
        }
    }

    public void llenarCategoriasHijoItem(int idCategoriaPadre) {

        List<Categoria> listaCategoriaHijo = categoriaService.findAllMenuChild(idCategoriaPadre);

        if (listaCategoriaHijo != null && !listaCategoriaHijo.isEmpty()) {

            listaCategoriaHijosItems = new ArrayList<>();

            for (Categoria cat : listaCategoriaHijo) {

                SelectItem item = new SelectItem();

                item.setValue(cat.getId());

                item.setLabel(cat.getNombre());

                listaCategoriaHijosItems.add(item);
            }
        }
    }

    public List<Producto> getLista() {
        return this.listaProductos;
    }
    
 
    public void valueChangeCategoriasPadre(ValueChangeEvent v) {
        this.idCategoriaPadreSeleccionada = (Integer) v.getNewValue();
        llenarCategoriasHijoItem(idCategoriaPadreSeleccionada);
    }

    public void valueChangeCategoriasHijo(ValueChangeEvent v) {
        this.idCategoriaHijoSeleccionada = (Integer) v.getNewValue();
    }

    public void valueChangeMarca(ValueChangeEvent v) {
        this.idMarcaSelecciona = (Integer) v.getNewValue();
    }

    /**
     * @return the sesiog
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
     * @return the image
     */
    public StreamedContent getImage() {
        SiArchivo archivo = archivoService.find(2);
        image = getBytesToStreamedContent(archivo);
        return image;
    }

}
