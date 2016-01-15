/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorodriguez
 */
@Entity
@SequenceGenerator(sequenceName = "GEN_VENTA_ID", name = "venta_Seq", allocationSize = 1)
@Table(name = "VENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findById", query = "SELECT v FROM Venta v WHERE v.id = :id"),
    @NamedQuery(name = "Venta.findByIdOrden", query = "SELECT v FROM Venta v WHERE v.ordenCompra.id = :idOrden AND v.eliminado = 'False'"),
    @NamedQuery(name = "Venta.findByImporteTotal", query = "SELECT v FROM Venta v WHERE v.importeTotal = :importeTotal"),
    @NamedQuery(name = "Venta.findByDescuento", query = "SELECT v FROM Venta v WHERE v.descuento = :descuento"),
    @NamedQuery(name = "Venta.findByConsecutivo", query = "SELECT v FROM Venta v WHERE v.consecutivo = :consecutivo"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Venta.findByHora", query = "SELECT v FROM Venta v WHERE v.hora = :hora"),
    @NamedQuery(name = "Venta.findByEliminado", query = "SELECT v FROM Venta v WHERE v.eliminado = :eliminado"),
    @NamedQuery(name = "Venta.findByCancelada", query = "SELECT v FROM Venta v WHERE v.cancelada = :cancelada")})
public class Venta implements Serializable {

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA")
    @Temporal(TemporalType.TIME)
    private Date hora;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "venat_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IMPORTE_TOTAL")
    private BigDecimal importeTotal;
    @Column(name = "DESCUENTO")
    private BigInteger descuento;
    @Size(max = 10)
    @Column(name = "CONSECUTIVO")
    private String consecutivo;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
//    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
//    @ManyToOne
//    private Estatus estatus;
    @Size(max = 5)
    @Column(name = "CANCELADA")
    private String cancelada;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne
    private Usuario usuario;
    @JoinColumn(name = "ORDEN_COMPRA", referencedColumnName = "ID")
    @ManyToOne
    private OrdenCompra ordenCompra;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "ID")
    @ManyToOne
    private Cliente cliente;

    public Venta() {
    }

    public Venta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigInteger getDescuento() {
        return descuento;
    }

    public void setDescuento(BigInteger descuento) {
        this.descuento = descuento;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.magno.entity.Venta[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    

    /**
     * @return the estatus
     */
//    public Estatus getEstatus() {
//        return estatus;
//    }
//
//    /**
//     * @param estatus the estatus to set
//     */
//    public void setEstatus(Estatus estatus) {
//        this.estatus = estatus;
//    }

    

}
