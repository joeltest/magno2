/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
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

        @SequenceGenerator(sequenceName = "GEN_OFERTA_ID", name = "oferta_Seq", allocationSize = 1)
@Table(name = "OFERTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o"),
    @NamedQuery(name = "Oferta.findById", query = "SELECT o FROM Oferta o WHERE o.id = :id"),
    @NamedQuery(name = "Oferta.findByNombre", query = "SELECT o FROM Oferta o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Oferta.findByFechaInicio", query = "SELECT o FROM Oferta o WHERE o.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Oferta.findByFechaFin", query = "SELECT o FROM Oferta o WHERE o.fechaFin = :fechaFin"),
    @NamedQuery(name = "Oferta.findByPorcentaje", query = "SELECT o FROM Oferta o WHERE o.porcentaje = :porcentaje"),
    @NamedQuery(name = "Oferta.findByCantidad", query = "SELECT o FROM Oferta o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Oferta.findByFoto", query = "SELECT o FROM Oferta o WHERE o.foto = :foto"),
    @NamedQuery(name = "Oferta.findByFechaGenero", query = "SELECT o FROM Oferta o WHERE o.fechaGenero = :fechaGenero"),
    @NamedQuery(name = "Oferta.findByHoraGenero", query = "SELECT o FROM Oferta o WHERE o.horaGenero = :horaGenero"),
    @NamedQuery(name = "Oferta.findByEliminado", query = "SELECT o FROM Oferta o WHERE o.eliminado = :eliminado")})
public class Oferta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "oferta_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "PORCENTAJE")
    private Long porcentaje;
    @Column(name = "CANTIDAD")
    private Long cantidad;
    @Size(max = 2048)
    @Column(name = "FOTO")
    private String foto;
    @Column(name = "FECHA_GENERO")
    @Temporal(TemporalType.DATE)
    private Date fechaGenero;
    @Column(name = "HORA_GENERO")
    @Temporal(TemporalType.TIME)
    private Date horaGenero;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaId")
    private Collection<DetalleOferta> detalleOfertaCollection;
    @JoinColumn(name = "SUCURSAL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sucursal sucursalId;

    public Oferta() {
    }

    public Oferta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Long porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    @XmlTransient
    public Collection<DetalleOferta> getDetalleOfertaCollection() {
        return detalleOfertaCollection;
    }

    public void setDetalleOfertaCollection(Collection<DetalleOferta> detalleOfertaCollection) {
        this.detalleOfertaCollection = detalleOfertaCollection;
    }

    public Sucursal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
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
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.Oferta[ id=" + id + " ]";
    }
    
}
