package com.magno.beans;

import com.magno.beans.Sesion;
import com.magno.servicios.AbstractFacade;
import static com.magno.utils.FacesUtils.addErrorMessage;
import static com.magno.utils.FacesUtils.addInfoMessage;
import static com.magno.utils.FacesUtils.getRequestParameter;
import com.magno.beans.constantes.Constantes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import lombok.Getter;
import lombok.Setter;

@ViewScoped
public abstract class BaseBean<T, I> {

    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    
    private Class<T> itemClass;
    private T selected;
    private List<T> items;
    @Getter
    @Setter
    private int idSeleccionado;
    @Getter
    @Setter
    private Constantes.CrudActions OPERACION;

    public BaseBean() {

    }

    public BaseBean(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public abstract AbstractFacade getServicioRemote();

    public abstract void updateLista();

    protected abstract boolean validationBeforeCreate();

    protected abstract boolean validationBeforeRemove();

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void create(ActionEvent event) {
        persist(OPERACION);
    }

    public void remove(ActionEvent event) {
        persist(OPERACION);
    }

    protected void persist(Constantes.CrudActions persistAction) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case GUARDAR:
                        executeMethod("create");
                        addInfoMessage("Se guadó", "satisfactoriamente");
                        this.setSelected(null);
                        break;
                    case MODIFICAR:
                        executeMethod("edit");
                        addInfoMessage("Se modificó", "satisfactoriamente");
                        this.setSelected(null);
                        break;
                    case ELIMINAR:
                        executeMethod("remove");
                        addInfoMessage("Se eliminó", "satisfactoriamente");
                        break;
                }
                setSelected(null);
            } catch (EJBException ex) {
                addErrorMessage("No se pudo completar la operación", "existio un conflicto");
            } catch (Exception ex) {
                addErrorMessage("No se pudo completar la operación", "existio un conflicto");
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void prepareNuevoRegistro(ActionEvent event) {
        prepareNuevoRegistro();
    }

    protected void prepareNuevoRegistro() {
        T newItem;
        try {
            setSelected(null);
            newItem = itemClass.newInstance();
            this.selected = newItem;
            OPERACION = Constantes.CrudActions.GUARDAR;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void prepareEditarRegistro(int id) {
        setSelected(find(id));
        OPERACION = Constantes.CrudActions.MODIFICAR;
    }

    protected boolean removeRelaciones(String... tablasValidarRelacion) {
//        if (crudRemote.eliminar(getSelected(), getSesion().getUsuarioSesion().getId(), tablasValidarRelacion)) {
//            refrescarLista();
//            return true;
//        } else {
        return false;
//        }
    }

    public void prepareModificacion(ActionEvent event) {
        getIdObjetoSeleccionado();
        OPERACION = Constantes.CrudActions.MODIFICAR;
    }

    public void prepareEliminacion(ActionEvent event) {
        getIdObjetoSeleccionado();
        OPERACION = Constantes.CrudActions.ELIMINAR;
    }

    public void clearSelected(ActionEvent event) {
        setSelected(null);
    }

    private void getIdObjetoSeleccionado() {
        //idSeleccionado = Integer.parseInt(getRequestAtributteParameter(event, "param"));
        idSeleccionado = Integer.parseInt(getRequestParameter("param"));
        if (idSeleccionado != 0) {
            setSelected(find(idSeleccionado));
        }
    }

    private void executeMethod(String metodo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
//        getServicioRemote().cre
        Method metodoService = getServicioRemote().getClass().getMethod(metodo, itemClass, Object.class);
        if (metodoService != null) {
            metodoService.invoke(getServicioRemote(), selected, getSesion().getUsuarioSesion().getId());
        }
    }

    public T find(Object id) {
        Object retorno = null;
        try {
            Method metodoFind = getServicioRemote().getClass().getMethod("find", Object.class);
            if (metodoFind != null) {
                retorno = metodoFind.invoke(getServicioRemote(), id);
            }
        } catch (IllegalAccessException ex) {
            System.out.println("Excepción al intentar consultar (findId - IllegalAccessException) "+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
             System.out.println("Excepción al intentar consultar IllegalArgumentException "+ ex.getMessage());
        } catch (InvocationTargetException ex) {
            System.out.println("Excepción al intentar consultar InvocationTargetException "+ ex.getMessage());
        } catch (NoSuchMethodException ex) {
            System.out.println("Excepción al intentar consultar NoSuchMethodException "+ ex.getMessage());
        }
        return (T) retorno;
    }

    protected T findValorPorCampo(String nombreCampo, Object valorBuscar) {
//        return crudRemote.traerValorPorCampo(nombreCampo, valorBuscar, itemClass);
        return null;
    }

    //metodos comunes
    protected int extractValueListener(ValueChangeEvent listener) {
        return (Integer) listener.getNewValue();
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
}
