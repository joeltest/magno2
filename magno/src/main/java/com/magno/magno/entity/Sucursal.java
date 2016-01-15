/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorodriguez
 */
@Entity
@Table(name = "SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
    @NamedQuery(name = "Sucursal.findById", query = "SELECT s FROM Sucursal s WHERE s.id = :id"),
    @NamedQuery(name = "Sucursal.findByNombre", query = "SELECT s FROM Sucursal s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sucursal.findByEstado", query = "SELECT s FROM Sucursal s WHERE s.estado = :estado"),
    @NamedQuery(name = "Sucursal.findByCiudad", query = "SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad"),
    @NamedQuery(name = "Sucursal.findByDireccion", query = "SELECT s FROM Sucursal s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sucursal.findByColonia", query = "SELECT s FROM Sucursal s WHERE s.colonia = :colonia"),
    @NamedQuery(name = "Sucursal.findByCalle", query = "SELECT s FROM Sucursal s WHERE s.calle = :calle"),
    @NamedQuery(name = "Sucursal.findByCp", query = "SELECT s FROM Sucursal s WHERE s.cp = :cp"),
    @NamedQuery(name = "Sucursal.findByEliminado", query = "SELECT s FROM Sucursal s WHERE s.eliminado = :eliminado")})
public class Sucursal implements Serializable {
    @OneToMany(mappedBy = "sucursal")
    private Collection<Consecutivo> consecutivoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 60)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 50)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 50)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 50)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 50)
    @Column(name = "CALLE")
    private String calle;
    @Size(max = 12)
    @Column(name = "CP")
    private String cp;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalId")
    private Collection<OrdenCompra> ordenCompraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalId")
    private Collection<Producto> productoCollection;
    @JoinColumn(name = "GERENTE", referencedColumnName = "ID")
    @ManyToOne
    private Usuario gerente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalId")
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalId")
    private Collection<Oferta> ofertaCollection;

    public Sucursal() {
    }

    public Sucursal(Integer id) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    @XmlTransient
    public Collection<OrdenCompra> getOrdenCompraCollection() {
        return ordenCompraCollection;
    }

    public void setOrdenCompraCollection(Collection<OrdenCompra> ordenCompraCollection) {
        this.ordenCompraCollection = ordenCompraCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @XmlTransient
    public Collection<Oferta> getOfertaCollection() {
        return ofertaCollection;
    }

    public void setOfertaCollection(Collection<Oferta> ofertaCollection) {
        this.ofertaCollection = ofertaCollection;
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
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.Sucursal[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Consecutivo> getConsecutivoCollection() {
        return consecutivoCollection;
    }

    public void setConsecutivoCollection(Collection<Consecutivo> consecutivoCollection) {
        this.consecutivoCollection = consecutivoCollection;
    }
    
}
