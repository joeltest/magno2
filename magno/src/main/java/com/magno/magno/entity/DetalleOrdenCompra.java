/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorodriguez
 */
@Entity
@SequenceGenerator(sequenceName = "GEN_DETALLE_ORDEN_ID", name = "detalleOrden_Seq", allocationSize = 1)
@Table(name = "DETALLE_ORDEN_COMPRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenCompra.findAll", query = "SELECT d FROM DetalleOrdenCompra d"),
    @NamedQuery(name = "DetalleOrdenCompra.findAllByIdeOrden", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.ordenCompraId.id = :idOrden"),
    @NamedQuery(name = "DetalleOrdenCompra.findById", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleOrdenCompra.findByVentaId", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.venta = :ventaId"),
    @NamedQuery(name = "DetalleOrdenCompra.findByPrecioFinal", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.precioFinal = :precioFinal"),
    @NamedQuery(name = "DetalleOrdenCompra.findByCantidad", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleOrdenCompra.findByDescuentoExtra", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.descuentoExtra = :descuentoExtra"),
    @NamedQuery(name = "DetalleOrdenCompra.findByCancelado", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.cancelado = :cancelado"),
    @NamedQuery(name = "DetalleOrdenCompra.findByMotivoCancelacio", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.motivoCancelacio = :motivoCancelacio")})
public class DetalleOrdenCompra implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Column(name = "IMPORTE")
    private BigDecimal importe;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "detalleOrden_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PRECIO_FINAL")
    private Long precioFinal;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "DESCUENTO_EXTRA")
    private Long descuentoExtra;
    @Size(max = 5)
    @Column(name = "CANCELADO")
    private String cancelado;
    @Size(max = 512)
    @Column(name = "MOTIVO_CANCELACIO")
    private String motivoCancelacio;
    @JoinColumn(name = "USUARIO_CANCELO", referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private Usuario usuarioCancelo;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "ORDEN_COMPRA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private OrdenCompra ordenCompraId;

    @JoinColumn(name = "VENTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Venta venta;

    public DetalleOrdenCompra() {
    }

    public DetalleOrdenCompra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Long precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getDescuentoExtra() {
        return descuentoExtra;
    }

    public void setDescuentoExtra(Long descuentoExtra) {
        this.descuentoExtra = descuentoExtra;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getMotivoCancelacio() {
        return motivoCancelacio;
    }

    public void setMotivoCancelacio(String motivoCancelacio) {
        this.motivoCancelacio = motivoCancelacio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenCompra)) {
            return false;
        }
        DetalleOrdenCompra other = (DetalleOrdenCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.magno.entity.DetalleOrdenCompra[ id=" + id + " ]";
    }

    /**
     * @return the usuarioCancelo
     */
    public Usuario getUsuarioCancelo() {
        return usuarioCancelo;
    }

    /**
     * @param usuarioCancelo the usuarioCancelo to set
     */
    public void setUsuarioCancelo(Usuario usuarioCancelo) {
        this.usuarioCancelo = usuarioCancelo;
    }

    /**
     * @return the productoId
     */
    public Producto getProductoId() {
        return productoId;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    /**
     * @return the ordenCompraId
     */
    public OrdenCompra getOrdenCompraId() {
        return ordenCompraId;
    }

    /**
     * @param ordenCompraId the ordenCompraId to set
     */
    public void setOrdenCompraId(OrdenCompra ordenCompraId) {
        this.ordenCompraId = ordenCompraId;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the venta
     */
    public Venta getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Venta venta) {
        this.venta = venta;
    }

}
