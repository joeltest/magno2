/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.ToString;

/**
 *
 * @author jorodriguez
 */
@ToString
@Entity
@SequenceGenerator(sequenceName = "GEN_SI_ARCHIVO_ID", name = "archivo_Seq", allocationSize = 1)
@Table(name = "SI_ARCHIVO")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "SiArchivo.findAll", query = "SELECT s FROM SiArchivo s"),
    @NamedQuery(name = "SiArchivo.findById", query = "SELECT s FROM SiArchivo s WHERE s.id = :id"),
    @NamedQuery(name = "SiArchivo.findByNombre", query = "SELECT s FROM SiArchivo s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SiArchivo.findByTipoArchivo", query = "SELECT s FROM SiArchivo s WHERE s.tipoArchivo = :tipoArchivo"),
    @NamedQuery(name = "SiArchivo.findByPeso", query = "SELECT s FROM SiArchivo s WHERE s.peso = :peso"),
    @NamedQuery(name = "SiArchivo.findByEliminado", query = "SELECT s FROM SiArchivo s WHERE s.eliminado = :eliminado")})
public class SiArchivo implements Serializable {
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "archivo_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 70)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 75)
    @Column(name = "TIPO_ARCHIVO")
    private String tipoArchivo;
    @Size(max = 10)
    @Column(name = "PESO")
    private String peso;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @OneToMany(mappedBy = "siArchivo")
    private Collection<Producto> productoCollection;

    public SiArchivo() {
    }

    public SiArchivo(Integer id) {
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

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }


    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
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
        if (!(object instanceof SiArchivo)) {
            return false;
        }
        SiArchivo other = (SiArchivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    
}
