/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.catalogos;

import com.magno.servicios.FacadeLocal;
import com.magno.utils.FacesUtils;
import com.magno.utils.MensajeUtils;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;


public abstract class BaseCrud<T> implements Serializable{

    enum CrudActions {

        INSERTAR, MODIFICAR, ELIMINAR,DETALLE
    };

    private Class<T> itemClass;
    @Getter
    @Setter
    private T selected;
    private List<T> items;
    @Getter
    @Setter
    private int idSeleccionado;
    @Getter
    @Setter
    private CrudActions OPERACION;

    
    protected abstract FacadeLocal<T> getService();
    
    public BaseCrud(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public void preprarNuevoRegistro() throws InstantiationException, IllegalAccessException {
        System.out.println("Preparar nuevo registro");
        T newItem;
        //setSelected(null);
        newItem = itemClass.newInstance();
        this.selected = newItem;
        OPERACION = CrudActions.INSERTAR;
    }
 
    
    public void guardar(ActionEvent event){
        System.out.println("@@guardar");
        System.out.println("@@ "+selected.toString());
        persistir();
        System.out.println("Se guardo ");
    }
    
    public void prepararModificacion(){
        recogerIdSeleccionado();
        OPERACION = CrudActions.MODIFICAR;
    }
    
    public void modificar(ActionEvent event){
        System.out.println("@@Modifcr");
        System.out.println("@@ "+selected.toString());
        persistir();
    }
    
    public void preprarDetalle(ActionEvent event){
        System.out.println("@@detalle");
           recogerIdSeleccionado();
           OPERACION = CrudActions.DETALLE;
    }
        
    public void prepararEliminacion(ActionEvent event) {
        recogerIdSeleccionado();
        OPERACION = CrudActions.ELIMINAR;
    }
    
    
    
    public void eliminar(ActionEvent event){
        //set eliminar
        
    }
    
    private void recogerIdSeleccionado() {        
        //FIXME : poner el metodo que recoge parametros de la vista
        
        idSeleccionado = Integer.parseInt(FacesUtils.getRequestParameter("idParam"));
        System.out.println("Parametro "+idSeleccionado);
        if (idSeleccionado != 0) {
            T encontrado = getService().find(idSeleccionado);
            setSelected(encontrado);
        }
    }   
    
    protected void persistir(){        
        System.out.println("Persistir");
        switch(OPERACION){
            case INSERTAR : 
                System.out.println("Operacion Insertar "+selected != null ? "NO es null":" es null" );
                System.out.println(("getService()  "+getService() != null) ? "NO servicio nukk":"no null");
                getService().create(selected);     
                MensajeUtils.addInfoMessage("Guardado", "Se guardó existosamente..");
                break;
            case MODIFICAR : 
                System.out.println("Operacion Insertar "+selected != null ? "es null":"no es null" );
                System.out.println(("getService()  "+getService() != null) ? "servicio nukk":"no null");
                getService().edit(selected);                        
                MensajeUtils.addInfoMessage("Modificación", "Se modificó existosamente..");
                break;
          case ELIMINAR : 
                System.out.println(("Eliminacion getService()  "+getService() != null) ? " NO servicio nukk":"no null");
                getService().edit(selected);                        
                break;
        }
        OPERACION =CrudActions.INSERTAR;
    }
    
    public List<T> getAllEntidades(){        
        return getService().findAll();
    }
        
    public void clearSelected() {
        setSelected(null);
    }  
}
