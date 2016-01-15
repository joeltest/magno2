/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorodriguez
 */
@Entity
@Table(name = "DETALLE_OFERTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleOferta.findAll", query = "SELECT d FROM DetalleOferta d"),
    @NamedQuery(name = "DetalleOferta.findById", query = "SELECT d FROM DetalleOferta d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleOferta.findByPrecioOferta", query = "SELECT d FROM DetalleOferta d WHERE d.precioOferta = :precioOferta"),
    @NamedQuery(name = "DetalleOferta.findByFechaGenero", query = "SELECT d FROM DetalleOferta d WHERE d.fechaGenero = :fechaGenero"),
    @NamedQuery(name = "DetalleOferta.findByHoraGenero", query = "SELECT d FROM DetalleOferta d WHERE d.horaGenero = :horaGenero"),
    @NamedQuery(name = "DetalleOferta.findByEliminado", query = "SELECT d FROM DetalleOferta d WHERE d.eliminado = :eliminado")})
public class DetalleOferta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PRECIO_OFERTA")
    private Long precioOferta;
    @Column(name = "FECHA_GENERO")
    @Temporal(TemporalType.DATE)
    private Date fechaGenero;
    @Column(name = "HORA_GENERO")
    @Temporal(TemporalType.TIME)
    private Date horaGenero;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "OFERTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Oferta ofertaId;

    public DetalleOferta() {
    }

    public DetalleOferta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(Long precioOferta) {
        this.precioOferta = precioOferta;
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

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Oferta getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Oferta ofertaId) {
        this.ofertaId = ofertaId;
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
        if (!(object instanceof DetalleOferta)) {
            return false;
        }
        DetalleOferta other = (DetalleOferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.DetalleOferta[ id=" + id + " ]";
    }
    
}
