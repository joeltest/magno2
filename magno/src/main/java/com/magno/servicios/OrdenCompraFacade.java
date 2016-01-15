/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.beans.ProductoVo;
import com.magno.magno.entity.Cliente;
import com.magno.magno.entity.DetalleOrdenCompra;
import com.magno.magno.entity.OrdenCompra;
import com.magno.beans.constantes.Constantes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorodriguez
 */
@Stateless
public class OrdenCompraFacade extends AbstractFacade<OrdenCompra> implements OrdenCompraFacadeLocal {
    @EJB
    private ConsecutivoFacadeLocal consecutivoService;
    @EJB
    private EstatusFacadeLocal estatusService;
    
    @EJB
    private SucursalFacadeLocal sucursarService;
    @EJB
    private DetalleOrdenCompraFacadeLocal detalleService;
    
    @EJB
    private ClienteFacadeLocal clienteService;
    
    @EJB
    private VentaFacadeLocal ventaService;
    
    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenCompraFacade() {
        super(OrdenCompra.class);
    }

    @Override
    public OrdenCompra guardarOrden(List<ProductoVo> listaProcProductoVo, double importeTotal, Cliente cliente,int idSucursal) {
        final OrdenCompra orden = new OrdenCompra();
        String consecutivo =  consecutivoService.obtenerConsecutivo(Constantes.CONSECUTIVO_ORDEN, idSucursal);
        orden.setCodigo(consecutivo);
        orden.setEstatusId(estatusService.find(Constantes.ESTATUS_ENVIADA));
        orden.setFechaGenero(new Date());
        orden.setHoraGenero(new Date());
        orden.setSucursalId(sucursarService.find(idSucursal));
        orden.setImporteTotal(new BigDecimal(importeTotal));        
        clienteService.create(cliente);
        orden.setClienteId(cliente);
        create(orden);
        
        //guardar detalle
        for(ProductoVo vo : listaProcProductoVo){
        
            final DetalleOrdenCompra detalle = new DetalleOrdenCompra();
            detalle.setOrdenCompraId(orden);
            detalle.setCantidad(vo.getCantidad());
            detalle.setProductoId(vo.getProducto());
            detalle.setCancelado(Constantes.BOOLEAN_FALSE);
            detalleService.create(detalle);
            
        }
        
        
        //generar la venta
        ventaService.crearVenta(orden);
        
        return orden;
                
    }
    //aqui enviar correo...

    @Override
    public List<OrdenCompra> findAll(int idEstatus) {
        return em.createNamedQuery("OrdenCompra.findAllByIdEstatus")
                .setParameter("idEstatus", idEstatus)
                .getResultList();
    }
    
}
