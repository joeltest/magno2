/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.utils;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jorodriguez
 */
public class UtilsRedirect {
      private static StringBuilder sb = new StringBuilder();

    public static String getActionRedireccion(String url) {
        sb.delete(0, sb.length());
        return sb.append(url).append("?faces-redirect=true").toString();
    }

    public static void redireccionarContexto(final String url) {
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = getUrl(origRequest);

        FacesContext fc = FacesContext.getCurrentInstance();

        try {
            fc.getExternalContext().redirect(prefix + url);
        } catch (IOException ex) {
            System.out.println("Error de IO al redireccionar: " + ex.getMessage());
        }
    }

    private static String getUrl(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80) {
            url += ":" + request.getServerPort();
        }
        return url;
    }
}
