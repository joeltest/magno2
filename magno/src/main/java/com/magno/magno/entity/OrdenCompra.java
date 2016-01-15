/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@SequenceGenerator(sequenceName = "GEN_ORDEN_ID", name = "orden_Seq", allocationSize = 1)
@Table(name = "ORDEN_COMPRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCompra.findAll", query = "SELECT o FROM OrdenCompra o"),
    @NamedQuery(name = "OrdenCompra.findAllByIdEstatus", query = "SELECT o FROM OrdenCompra o WHERE o.estatusId.id = :idEstatus"),
    @NamedQuery(name = "OrdenCompra.findById", query = "SELECT o FROM OrdenCompra o WHERE o.id = :id"),
    @NamedQuery(name = "OrdenCompra.findByCodigo", query = "SELECT o FROM OrdenCompra o WHERE o.codigo = :codigo"),
    @NamedQuery(name = "OrdenCompra.findByFechaGenero", query = "SELECT o FROM OrdenCompra o WHERE o.fechaGenero = :fechaGenero"),
    @NamedQuery(name = "OrdenCompra.findByHoraGenero", query = "SELECT o FROM OrdenCompra o WHERE o.horaGenero = :horaGenero")})

public class OrdenCompra implements Serializable {
    @OneToMany(mappedBy = "ordenCompra")
    private Collection<Venta> ventaCollection;
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Cliente clienteId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IMPORTE_TOTAL")
    private BigDecimal importeTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenCompraId")
    private Collection<DetalleOrdenCompra> detalleOrdenCompraCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "orden_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "FECHA_GENERO")
    @Temporal(TemporalType.DATE)
    private Date fechaGenero;
    @Column(name = "HORA_GENERO")
    @Temporal(TemporalType.TIME)
    private Date horaGenero;
    @JoinColumn(name = "SUCURSAL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sucursal sucursalId;
    @JoinColumn(name = "ESTATUS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estatus estatusId;

    public OrdenCompra() {
    }

    public OrdenCompra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaGenero() {
        return fechaGenero;
    }

    public void setFechaGenero(Date fechaGenero) {
        this.fechaGenero = fechaGenero;
    }

    public Date getHoraGenero() {
        return horaGenero;
    }

    public void setHoraGenero(Date horaGenero) {
        this.horaGenero = horaGenero;
    }

    @XmlTransient
    public Collection<DetalleOrdenCompra> getDetalleOrdenCompraCollection() {
        return detalleOrdenCompraCollection;
    }

    public void setDetalleOrdenCompraCollection(Collection<DetalleOrdenCompra> detalleOrdenCompraCollection) {
        this.detalleOrdenCompraCollection = detalleOrdenCompraCollection;
    }

    public Sucursal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Estatus getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Estatus estatusId) {
        this.estatusId = estatusId;
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
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        OrdenCompra other = (OrdenCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.OrdenCompra[ id=" + id + " ]";
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    
}
