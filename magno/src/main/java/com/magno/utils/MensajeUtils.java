/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.utils;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jorodriguez
 */
public class MensajeUtils {
//     protected String getKeyResourceBundle(String key) {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        ResourceBundle resourceBundle = ResourceBundle.getBundle(
//                "sistema/literales.messages",
//                fc.getViewRoot().getLocale());
//        return resourceBundle.getString(key);
//    }

     public static void addInfoMessage(String msgPrincipal, String msgSecundario) {
        addInfoMessageId(null, msgPrincipal, msgSecundario);
    }

     public static void addWarningMessage(String msgPrincipal, String msgSecundario) {
        addWarningMessageId(null, msgPrincipal, msgSecundario);
    }

    public static void addErrorMessage(String msgPrincipal, String msgSecundario) {
        addErrorMessageId(null, msgPrincipal, msgSecundario);
    }

    public static void addFatalMessage(String msgPrincipal, String msgSecundario) {
        addFatalMessageId(null, msgPrincipal, msgSecundario);
    }

    /*
     *Metodos que agregan un mensaje a un id de componente  
     * @param clientId
     * @param msgPrincipal
     * @param msgSecundario 
     */
    public static void addInfoMessageId(String clientId, String msgPrincipal, String msgSecundario) {
        
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msgPrincipal, msgSecundario));
    }

    public static void addWarningMessageId(String clientId, String msgPrincipal, String msgSecundario) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_WARN, msgPrincipal, msgSecundario));
    }

    public static void addErrorMessageId(String clientId, String msgPrincipal, String msgSecundario) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgPrincipal, msgSecundario));
    }

    public static void addFatalMessageId(String clientId, String msgPrincipal, String msgSecundario) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_FATAL, msgPrincipal, msgSecundario));
    }

//    protected String getRequestParameter(String paramName) {
//        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
//    }
//
//    protected Object getRequestAtributteParameter(ActionEvent actionEvent, String paramName) {
//        return actionEvent.getComponent().getAttributes().get(paramName);
//    }

    /**
     * Metodo que recoge un parametro de entrada por el metodo setFlashParam
     *
     * @param key
     * @return
     */
//    protected Object getFlashParam(String key) {
//        return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
//    }
//
//    protected void setFlashParam(String key, Object value) {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);
//    }

}
