/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.beans;

import com.magno.servicios.VentaFacadeLocal;
import com.magno.vo.VentaVo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author jorodriguez
 */
//@Named(value = "consultaVentas")
@ManagedBean
@SessionScoped
public class ConsultaVentas implements Serializable{
    
    @ManagedProperty(value = "#{sesion}")
    private Sesion sesion;
    
    @EJB
    private VentaFacadeLocal ventaServicio;
   
    @Getter
    @Setter
    private List<VentaVo> listaVentaVo;
    
    @Getter
    @Setter
    private Date fechaInicio;
   
    @Getter
    @Setter
    private Date fechaFin;
    
    //graficas
    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;

    private void createAnimatedModels() {
        setAnimatedModel1(initLinearModel());
        getAnimatedModel1().setTitle("Line Chart");
        getAnimatedModel1().setAnimate(true);
        getAnimatedModel1().setLegendPosition("se");
        
        Axis yAxis = getAnimatedModel1().getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
         
        setAnimatedModel2(initBarModel());
        getAnimatedModel2().setTitle("Ventas y Ordenes");
        getAnimatedModel2().setAnimate(true);
        getAnimatedModel2().setLegendPosition("ne");
        yAxis = getAnimatedModel2().getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    
     private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries ventas = new ChartSeries();
        ventas.setLabel("Ventas");
        for(VentaVo v : listaVentaVo ){
            SimpleDateFormat dm = new SimpleDateFormat("dd-MM-yyyy");                    
            ventas.set(dm.format(v.getVenta().getFecha()), v.getVenta().getImporteTotal().doubleValue());
        }
        
        ChartSeries ordenLista = new ChartSeries();
        
        ordenLista.setLabel("Ordenes");
        
        for(VentaVo v : listaVentaVo ){
            SimpleDateFormat dm = new SimpleDateFormat("dd-MM-yyyy");                    
            /*String str = v.getVenta().getOrdenCompra().getCodigo()
                    +" "
                    +v.getVenta().getOrdenCompra().getClienteId().getNombre()
                    +" "+v.getVenta().getOrdenCompra().getImporteTotal().doubleValue();*/
            
            ordenLista.set(dm.format(v.getVenta().getOrdenCompra().getFechaGenero()),
                    v.getVenta().getOrdenCompra().getImporteTotal().doubleValue());
        }

 
        model.addSeries(ventas);
        model.addSeries(ordenLista);
         
        return model;
    }
     
     private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }
    
    /**
     * Creates a new instance of ConsultaVentas
     */
    
    public ConsultaVentas() {
    }
    
    public void buscarVentas(ActionEvent event){
        System.out.println("Buscar ");
        System.out.println(" "+sesion.getUsuarioSesion().getSucursalId().getId());
        System.out.println(" "+fechaInicio);
        System.out.println(" "+fechaFin);
            listaVentaVo = ventaServicio.findAll(sesion.getUsuarioSesion().getSucursalId().getId(), fechaInicio, fechaFin);
            createAnimatedModels();
    }

    /**
    
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

    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }

    public void setAnimatedModel1(LineChartModel animatedModel1) {
        this.animatedModel1 = animatedModel1;
    }

    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }

    public void setAnimatedModel2(BarChartModel animatedModel2) {
        this.animatedModel2 = animatedModel2;
    }

       
}
