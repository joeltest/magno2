/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.servicios;

import com.magno.magno.entity.Estatus;
import com.magno.magno.entity.OrdenCompra;
import com.magno.magno.entity.Usuario;
import com.magno.magno.entity.Venta;
import com.magno.vo.VentaVo;
import com.magno.beans.constantes.Constantes;
import java.util.ArrayList;
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
public class VentaFacade extends AbstractFacade<Venta> implements VentaFacadeLocal {

    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    @EJB
    private DetalleOrdenCompraFacade detalleCompraService;

    @EJB
    private ConsecutivoFacadeLocal consecutivoService;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }

    @Override
    public void crearVenta(OrdenCompra orden) {

        Venta venta = new Venta();
        String consecutivo = consecutivoService.obtenerConsecutivo(Constantes.CONSECUTIVO_VENTA, orden.getSucursalId().getId());
        venta.setConsecutivo(consecutivo);
        venta.setCancelada(Constantes.BOOLEAN_FALSE);
        venta.setCliente(orden.getClienteId());
        venta.setImporteTotal(orden.getImporteTotal());
        //venta.setUsuario(orden.get);
        venta.setFecha(new Date());
        venta.setHora(new Date());
        venta.setOrdenCompra(orden);
//        venta.setEstatus(new Estatus(Constantes.ESTATUS_CANCELADA));
        create(venta);

    }

    @Override
    public Venta findByOrden(int idOrden) {
        return (Venta) em.createQuery("Venta.findByIdOrden")
                .setParameter("idOrden", idOrden)
                .getSingleResult();
    }

//    @Override
//    public void cancelarVenta(int idVenta, int idUsuario) {
//        
//        final Venta venta = find(idVenta);
//        
//        venta.setCancelada(Constantes.BOOLEAN_TRUE);
//        venta.setUsuario(new Usuario(idUsuario));
//        venta.setEstatus(new Estatus(Constantes.ESTATUS_CANCELADA));
//        edit(venta);
//    
//    }
    @Override
    public List<VentaVo> findAll(int idSucursal, Date fechaInicio, Date fechaFin) {
        List<Venta> listaVenta = em.createQuery("SELECT v FROM Venta v "
                + " WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin "
                + " AND v.ordenCompra.sucursalId.id = :idSucursal"
                + " AND v.cancelada = 'False' ")
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        List<VentaVo> listaRetorno = new ArrayList<>();

        for (Venta venta : listaVenta) {
            VentaVo vo = new VentaVo();
            vo.setVenta(venta);
            vo.setDetalle(detalleCompraService.findByVenta(venta.getId()));
            listaRetorno.add(vo);
        }

        return listaRetorno;
    }

}
