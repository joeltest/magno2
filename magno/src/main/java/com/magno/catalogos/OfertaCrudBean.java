/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.beans.Sesion;
import com.magno.beans.constantes.Constantes;
import com.magno.magno.entity.Marca;
import com.magno.magno.entity.Oferta;
import com.magno.magno.entity.SiArchivo;
import com.magno.magno.entity.Sucursal;
import com.magno.servicios.FacadeLocal;
import com.magno.servicios.OfertaFacadeLocal;
import com.magno.servicios.SiArchivoFacadeLocal;
import com.magno.servicios.SucursalFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ihsa
 */
@ManagedBean
@SessionScoped
public class OfertaCrudBean extends BaseCrud<Oferta> {
   @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    
    @EJB
    OfertaFacadeLocal servicio;
    
    @EJB
    SiArchivoFacadeLocal servicioArchivo;
    
    @EJB
    SucursalFacadeLocal sucursalServicio;
    
     @Getter
    @Setter
    private List<SelectItem> listaSucursalItems = null;
    
    @Getter
    @Setter
    private List<Oferta> listaOfertas;
     
     @Getter
    @Setter
     private int idSucursalSeleccionado;
     
     
     @Getter
     @Setter
     private SiArchivo siArchivo;

    public OfertaCrudBean() {
        super(Oferta.class);
    }

    @PostConstruct
    public void init() {
        try {
            preprarNuevoRegistro();
            
            llenarSucursalItem();
        } catch (InstantiationException ex) {
            Logger.getLogger(OfertaCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OfertaCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prepararNuevo(ActionEvent e) throws InstantiationException, IllegalAccessException{
        
        this.siArchivo = new SiArchivo();
        
        preprarNuevoRegistro();
    }
    
    public void prepararModificacion(ActionEvent e) throws InstantiationException, IllegalAccessException{
        prepararModificacion();
        this.siArchivo = getSelected().getSiArchivo();
    }
    
    
    
    public void antesGuardar(ActionEvent e) throws InstantiationException, IllegalAccessException{
        System.out.println("AAAAAAAA");
        servicioArchivo.create(siArchivo);
        System.out.println("Archvo "+siArchivo.getId());
        
        getSelected().setSiArchivo(siArchivo);
        
        getSelected().setSucursalId(new Sucursal(idSucursalSeleccionado));
        
        getSelected().setEliminado("False");
        
        guardar(e);
        
        preprarNuevoRegistro();
    }
    
    public void modificarRegistro(ActionEvent event){
        getSelected().setSiArchivo(siArchivo);
        
        modificar(event);
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
     
     public void uploadAttachment(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        siArchivo.setArchivo(file.getContents());
        siArchivo.setNombre(file.getFileName());
        siArchivo.setTipoArchivo(file.getContentType());
        siArchivo.setEliminado(Constantes.ELIMINADO_FALSE);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("Archivo cargado: %s ", file.getFileName()),
                ""));

    }

     
    public void valueChangeSucursal(ValueChangeEvent v) {
        this.idSucursalSeleccionado = (Integer) v.getNewValue();
    }
    @Override
    protected FacadeLocal<Oferta> getService() {
        return servicio;
    }

    public List<Oferta> getListaOfertas(){
        return servicio.listaOfertas(sesion.getSucursalActiva().getId());
    }
    

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    
}

