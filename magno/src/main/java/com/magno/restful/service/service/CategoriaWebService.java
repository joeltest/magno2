/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.restful.service.service;

import com.google.gson.Gson;
import com.magno.magno.entity.Categoria;
import com.magno.servicios.CategoriaFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONException;

/**
 *
 * @author jorodriguez 
 * 
 * Se usan las librerias jackson-core-asl
 *
 * NOTA IMPORTANTE : se debe de registrar la libreria en el web.xml <servlet>
 * <servlet-name>ServletAdaptor</servlet-name>
 * <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
 * <load-on-startup>1</load-on-startup> </servlet>
 *
 */
@Stateless
@LocalBean
@Path("servicioCategoria")
public class CategoriaWebService {

    @EJB
    private CategoriaFacadeLocal categoriaService; 

    @GET
    @Path("/traerCategoriasPadres")
    @Produces(MediaType.APPLICATION_JSON) //<<<---- es el tipo de valor de retorno en este caso es Formato JSON
    public StringBuffer findAllCategoriaPadre() throws JSONException {
        try {
            System.out.println("Categoria padre");

            List<Categoria> categoriasPadre = this.categoriaService.findAllMenu();
            Gson gson = new Gson();
            String complex = gson.toJson(categoriasPadre);
            StringBuffer sb = new StringBuffer(complex);
            System.out.println("Complex " + complex);
            return sb;
        } catch (Exception e) {
            System.out.println("excepcion al traer las categorigas  " + e.getMessage());
            return  null;
        }
    }
    
    @GET
    @Path("/traerCategoriasHijo")
    @Produces(MediaType.APPLICATION_JSON) //<<<---- es el tipo de valor de retorno en este caso es Formato JSON
    public StringBuffer findAllCategoriaHijo(@QueryParam("idMenuChild") Integer menuChild) throws JSONException {
        try {
            System.out.println("Categoria padre "+menuChild);

            List<Categoria> categoriasHijo = this.categoriaService.findAllMenuChild(menuChild);
            Gson gson = new Gson();
            String complex = gson.toJson(categoriasHijo);
            StringBuffer sb = new StringBuffer(complex);
            System.out.println("Hijo " + complex);
            return sb;
        } catch (Exception e) {
            System.out.println("excepcion al traer las categorigas de pozos " + e.getMessage());
            return null;
        }
    }
    //Un metodo tipo GET con parametro incluido
    //@PathParam("parametro") Integer parametro
    //@Path("/traerJsonParam/{parametro}")

//    @GET
//    @Path("/traerJsonParam")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String traerJsonParam(@QueryParam("parametro") Integer parametro) throws JSONException {
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + parametro);
//        return "{\"markerId\":2,\"latitud\":24.9565627,\"longitud\":-98.0054476,\"textHtml\":\"<p>JSON Con el parametro " + parametro + " ..</p>\"}";
//    }
//
//    @GET
//    @Path("/graficaMensual")
//    @Produces(MediaType.APPLICATION_JSON)
//    //private String generaGraficaMensual(@QueryParam("parametro") int idProduccion) throws org.icefaces.ace.json.JSONException {
//    public String generaGraficaMensual(@QueryParam("parametro") Integer idProduccion) throws org.icefaces.ace.json.JSONException {
//
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + idProduccion);
//
//        gas = new ArrayList<Double>();
//
//        condensado = new ArrayList<Double>();
//        agua = new ArrayList<Double>();
//        fechas = new ArrayList<Date>();
//        mes = new ArrayList<Integer>();
//        cadenaMes = new ArrayList<String>();
//        anio = new ArrayList<Integer>();
//        fechaMes = new ArrayList<String>();
//        String json = null;
//        JSONObject j;
//        j = new JSONObject();
//        Date inicio = siManejoFechaLocal.fechaRestarMes(new Date(), 24);
//        Date fin = new Date();
//        System.out.println("mensual : " + "SI");
//        List<ProduccionDetalleVO> listaPozo = opProduccionRemote.traerProduccionMensual(idProduccion, inicio, fin);
//        //reporteModel.setRestoGrafica(false);
//        //reporteModel.setLista(new ListDataModel(listaPozo));
//        System.out.println("Lista recupera datos: " + listaPozo.size());
//
//        for (ProduccionDetalleVO produccionDetalleVO : listaPozo) {
//            gas.add(produccionDetalleVO.getGas());
//            condensado.add(produccionDetalleVO.getCondensado());
//            agua.add(produccionDetalleVO.getAgua());
//            fechas.add(produccionDetalleVO.getFecha());
//            mes.add(produccionDetalleVO.getMes());
//            cadenaMes.add(produccionDetalleVO.getCadenaMes());
//            anio.add(produccionDetalleVO.getAnio());
//            fechaMes.add(produccionDetalleVO.getCadenaMes() + "-" + produccionDetalleVO.getAnio());
//        }
//        j.put("gas", gas);
//        j.put("condensado", condensado);
//        j.put("agua", agua);
//        j.put("jfechas", fechaMes);
//        json = j.toString();
//        return json;
//    }
//
//    @GET
//    @Path("/graficaMensualPorArena")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String generaGraficaMensualPorArena(@QueryParam("parametro") String produccion) throws org.icefaces.ace.json.JSONException {
//        String[] cad = produccion.split(":");
//        int idProduccion = Integer.parseInt(cad[0]);
//        int idGrupoArena = Integer.parseInt(cad[1]);
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + idProduccion + "   - Arena -- " + idGrupoArena);
//
//        gas = new ArrayList<Double>();
//
//        condensado = new ArrayList<Double>();
//        agua = new ArrayList<Double>();
//        fechas = new ArrayList<Date>();
//        mes = new ArrayList<Integer>();
//        cadenaMes = new ArrayList<String>();
//        anio = new ArrayList<Integer>();
//        fechaMes = new ArrayList<String>();
//        String json = null;
//        JSONObject j;
//        j = new JSONObject();
//        Date inicio = siManejoFechaLocal.fechaRestarMes(new Date(), 24);
//        Date fin = new Date();
//        System.out.println("mensual : " + "SI");
//        List<ProduccionDetalleVO> listaPozo = opProduccionRemote.traerProduccionMensualPorArena(idProduccion, idGrupoArena, inicio, fin);
//        //reporteModel.setRestoGrafica(false);
//        //reporteModel.setLista(new ListDataModel(listaPozo));
//        System.out.println("Lista recupera datos: " + listaPozo.size());
//
//        for (ProduccionDetalleVO produccionDetalleVO : listaPozo) {
//            gas.add(produccionDetalleVO.getGas());
//            condensado.add(produccionDetalleVO.getCondensado());
//            agua.add(produccionDetalleVO.getAgua());
//            fechas.add(produccionDetalleVO.getFecha());
//            mes.add(produccionDetalleVO.getMes());
//            cadenaMes.add(produccionDetalleVO.getCadenaMes());
//            anio.add(produccionDetalleVO.getAnio());
//            fechaMes.add(produccionDetalleVO.getCadenaMes() + "-" + produccionDetalleVO.getAnio());
//        }
//        j.put("gas", gas);
//        j.put("condensado", condensado);
//        j.put("agua", agua);
//        j.put("jfechas", fechaMes);
//        json = j.toString();
//        return json;
//    }
//
//    @GET
//    @Path("/graficaDiaria")
//    @Produces(MediaType.APPLICATION_JSON)
//    //private String generaGraficaMensual(@QueryParam("parametro") int idProduccion) throws org.icefaces.ace.json.JSONException {
//    public String generaGraficaDiaria(@QueryParam("parametro") Integer idProduccion) throws org.icefaces.ace.json.JSONException {
//
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + idProduccion);
//
//        gas = new ArrayList<Double>();
//        condensado = new ArrayList<Double>();
//        agua = new ArrayList<Double>();
//        fechaMes = new ArrayList<String>();
//        String json = null;
//        JSONObject j;
//        j = new JSONObject();
//        Date inicio = siManejoFechaLocal.fechaRestarDias(new Date(), 200);
//        Date fin = new Date();
//        List<ProduccionDetalleVO> listaPozo = opProduccionRemote.traerProduccionDiaria(idProduccion, inicio, fin);
//        System.out.println("Diaria   : " + "SI");
//        //reporteModel.setRestoGrafica(false);
//        //reporteModel.setLista(new ListDataModel(listaPozo));
//        System.out.println("Lista recupera datos: " + listaPozo.size());
//
//        for (ProduccionDetalleVO produccionDetalleVO : listaPozo) {
//            gas.add(produccionDetalleVO.getGas());
//            condensado.add(produccionDetalleVO.getCondensado());
//            agua.add(produccionDetalleVO.getAgua());
//            fechaMes.add(Constantes.FMT_ddMMyyy.format(produccionDetalleVO.getFecha()));
//
//        }
//        j.put("gas", gas);
//        j.put("condensado", condensado);
//        j.put("agua", agua);
//        j.put("jfechas", fechaMes);
//        json = j.toString();
//        return json;
//    }
//
//    @GET
//    @Path("/graficaDiariaPorArena")
//    @Produces(MediaType.APPLICATION_JSON)
//    //private String generaGraficaMensual(@QueryParam("parametro") int idProduccion) throws org.icefaces.ace.json.JSONException {
//    public String generaGraficaDiariaPorArena(@QueryParam("parametro") String produccion) throws org.icefaces.ace.json.JSONException {
//        String[] cad = produccion.split(":");
//        int idProduccion = Integer.parseInt(cad[0]);
//        int idGrupoArena = Integer.parseInt(cad[1]);
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + idProduccion + " - - Arena - - " + idGrupoArena);
//
//        gas = new ArrayList<Double>();
//        condensado = new ArrayList<Double>();
//        agua = new ArrayList<Double>();
//        fechaMes = new ArrayList<String>();
//        String json = null;
//        JSONObject j;
//        j = new JSONObject();
//        Date inicio = siManejoFechaLocal.fechaRestarDias(new Date(), 200);
//        Date fin = new Date();
//        List<ProduccionDetalleVO> listaPozo = opProduccionRemote.traerProduccionDiariaPorArena(idProduccion, idGrupoArena, inicio, fin);
//        System.out.println("Diaria   : " + "SI");
//        System.out.println("Lista recupera datos: " + listaPozo.size());
//
//        for (ProduccionDetalleVO produccionDetalleVO : listaPozo) {
//            gas.add(produccionDetalleVO.getGas());
//            condensado.add(produccionDetalleVO.getCondensado());
//            agua.add(produccionDetalleVO.getAgua());
//            fechaMes.add(Constantes.FMT_ddMMyyy.format(produccionDetalleVO.getFecha()));
//
//        }
//        j.put("gas", gas);
//        j.put("condensado", condensado);
//        j.put("agua", agua);
//        j.put("jfechas", fechaMes);
//        json = j.toString();
//        return json;
//    }
//
//    @POST
//    @Path("/agregarComentarioPresion")
//    @Produces(MediaType.APPLICATION_JSON)
//    //private String generaGraficaMensual(@QueryParam("parametro") int idProduccion) throws org.icefaces.ace.json.JSONException {
//    public void agregarComentarioPresion(@QueryParam("parametro") String detallePresion) throws org.icefaces.ace.json.JSONException {
//        String[] cad = detallePresion.split(":");
//        int idDetallePresion = Integer.parseInt(cad[0]);
//        String sesionId = cad[1];
//        String comentario = cad[2];
//        System.out.println("idDetalle: " + idDetallePresion);
//        System.out.println("User: " + sesionId);
//        System.out.println("Com: " + comentario);
//        opPresionRemote.completarAgragarComentario(sesionId, idDetallePresion, comentario);
//    }
//
//    @GET
//    @Path("/graficaProfundidadDiasProgramado")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String graficaProfundidadDiasProgramado(@QueryParam("idPozo") Integer idPozo) {
//        System.out.println("CONSULTANDO JSON POR MEDIO DE LA URL - CON PARAMETRO ENTERO " + idPozo);
////        int escala;
//        
//        try {
//            List<OpActividadesPlantillaVo> listaActividades = opPlantillaPozoRemote.getAllActividadesPorIdPozo(idPozo);
////            List<Integer> l = new ArrayList<Integer>();
//            OpPozo pozo = opPozoRemote.find(idPozo);
////            escala = listaActividades.get(listaActividades.size()-1).getDiasAcumulados().intValue()+1;
////            for(int x=0;x<escala;x++){
////                l.add(x);
////            }
//            //Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
//            Gson gson = new Gson();
//            //String json = gson.toJson(listaActividades);
//            String json = gson.toJson(listaActividades);
//            //String json = gson.toJson(jObject);            
//            System.out.println("****** " + json + " ******");
//            System.out.println("Json para grafica : " + json);
//            return json;
//        } catch (Exception e) {
//            System.out.println("excepcion al traer json para grafica de profundidad " + e.getMessage());
//            return "";
//        }
//
//    }
//
//    @GET
//    @Path("/graficaProfundidadDensidadProgramado")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String graficaProfundidadDensidadProgramado(@QueryParam("idPozo") Integer idPozo)  {                
//       try {
//            List<OpPlantillaEtapaVo>  listaCurva = opEtapaPozoRemote.getCurvaDensidadPorPozo(idPozo);
//            OpPlantillaEtapaVo vo=  new OpPlantillaEtapaVo();
//            vo.setValorDensidad(new Double(0));
//            vo.setValorEtapa(new Double(0));
//            vo.setId(listaCurva.get(0).getId());
//            vo.setIdCurvaDensidad(listaCurva.get(0).getIdCurvaDensidad());
//            vo.setNuevo(false);
//            vo.setNombre(listaCurva.get(0).getNombre());
//            listaCurva.add(0, vo);
//            OpPozo pozo = opPozoRemote.find(idPozo);
//            Gson gson = new Gson();
//            String json = gson.toJson(listaCurva);
//            System.out.println("****** " + json + " ******");
//            System.out.println("Json para grafica : " + json);
//            return json;
//        } catch (Exception e) {
//            System.out.println("excepcion al traer  " + e.getMessage());
//            return "";
//        }
//    }
//
//    @POST
//    @Path("/agregarComentarioPrueba")
//    @Produces(MediaType.APPLICATION_JSON)
//    //private String generaGraficaMensual(@QueryParam("parametro") int idProduccion) throws org.icefaces.ace.json.JSONException {
//    public void agregarComentarioPreuba(@QueryParam("parametro") String prueba) throws org.icefaces.ace.json.JSONException {
//        String[] cad = prueba.split(":");
//        int idMedida = Integer.parseInt(cad[0]);
//        String sesionId = cad[1];
//        String comentario = cad[2];
//        System.out.println("id: " + idMedida);
//        System.out.println("User: " + sesionId);
//        System.out.println("Com: " + comentario);
//        opMedicionRealPozoRemote.completarAgragarComentario(sesionId, idMedida, comentario);
//    }
}