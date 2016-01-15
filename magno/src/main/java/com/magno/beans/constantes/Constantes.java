/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans.constantes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author sluis
 */
public class Constantes {

    public static final int ID_SUCURSAL_OMETEPEC = 1;
    
    public static final String BOOLEAN_TRUE = "True";
    public static final String BOOLEAN_TRUE_APOSTROFE = "'True'";
    
    public static final String CONSECUTIVO_ORDEN = "ORDEN";
    public static final String CONSECUTIVO_VENTA = "VENTA";
    
    public static final int ESTATUS_ENVIADA = 1;
    public static final int ESTATUS_SURTIDA = 2;
    public static final int ESTATUS_CANCELADA = 3;
    public static final int ESTATUS_ELIMINADA = 4;
            
    /**
     * false
     */
    public static final String BOOLEAN_FALSE = "False";
    public static final String BOOLEAN_FALSE_APOSTROFE = "'False'";
    
    public static final String NULL = "NULL";
    public static final String NULL_minus = "null";    
    public static final int CERO = 0;
    public static final int MENOS_UNO = -1;

    public static final String ELIMINADO_FALSE = BOOLEAN_FALSE;    
    public static final String ELIMINADO_TRUE = BOOLEAN_TRUE;
    
    public static final int ID_MODULO_SISCOP = 1;    
    
    public static final String POZO_PLANEADO = "Planeado";
    public static final int ID_POZO_PLANEADO = 1;
    public static final String POZO_OPERATIVO = "En Operacion";
    public static final int ID_POZO_OPERATIVO = 2;
    public static final String CATALOGO_POZOS = "Catálogo de Pozos";
    public static final String MAYOR_CERO = "Debe ser mayor que 0";
    public static final String SELECCIONE_OPCION = "Debe seleccionar una opción";
    public static final String REDIRECT_PRINCIPAL = "/principal?faces-redirect=true";
    
    
    
    /*
     * ======================== Constantes para Formatos de Fecha - INICIO ================================
     */
    /**
     * Formato para fecha - dd/MM/yyyy Ejemplo: 19/08/2012
     */
    public static final SimpleDateFormat FMT_ddMMyyy = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Formato para hora - HH:mm:ss Ejemplo: 12:05:23
     */
    public static final SimpleDateFormat FMT_HHmmss = new SimpleDateFormat("HH:mm:ss");
    /**
     * *
     * Formato que se usa para los archivos de log
     */
    public static final SimpleDateFormat FMT_ddMMyyyyHHmmss = new SimpleDateFormat("dd.MM.yyyy hh-mm-ss");
    /**
     * Formato para hora - HH:mm:ss Ejemplo: 12:05 (Sin Segundos)
     */
    public static final SimpleDateFormat FMT_hmm_a = new SimpleDateFormat("h:mm a");
    /**
     * Formato texto para hora - dd 'de' MMMMM 'de' yyyy Ejemplo:
     */
    public static final SimpleDateFormat FMT_TextDate = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", new Locale("es", "ES"));
    /**
     * Formato texto para hora - EEEE 'de' MMMMM 'de' yyyy
     */
    public static final SimpleDateFormat FMT_TextDateLarge = new SimpleDateFormat("EEEE dd 'de' MMMMM 'de' yyyy", new Locale("es", "ES"));
    /*
     * ======================== Constantes para Formatos de Moneda - INICIO ================================
     */
    /**
     * Formato general para Moneda ó Montos Ejemplo: 5968.90 <--Solo 2 decimales
     */
    public static final DecimalFormat formatoMoneda = new DecimalFormat("###,###,###.##");
    /*
     * ======================== Constantes para Formatos de Moneda - FIN    * ================================
     */
    /*
     * ======================== Constantes para el manejo de id de eventos de     * log - INICIO ================================
     */
    public static final int EVENTO_CREAR = 1;
    public static final int EVENTO_MODIFICAR = 2;
    public static final int EVENTO_ELIMINAR = 3;

    //
    public static String[] MESES =  {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
 
    
  
    public static enum CrudActions {INSERTAR,GUARDAR,MODIFICAR, ELIMINAR}
 
    public static String MENSAJE_EXCEPCION = "* * * * * > Excepción al intentar ";


    public static int ID_ROL_CAPTURISTA = 1;
    
    //-id de rol en la base de datos
    public static int ID_ROL_ADMINISTRADOR = 2;
    
    public static int ID_ROL_GENERAL = 3;
    
    
    
}

