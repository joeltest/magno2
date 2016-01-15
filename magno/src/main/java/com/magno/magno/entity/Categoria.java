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
@SequenceGenerator(sequenceName = "GEN_CATEGORIA_ID", name = "categoria_Seq", allocationSize = 1)
@Table(name = "CATEGORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findAllMenu", query = "SELECT c FROM Categoria c WHERE c.categoriaPadre IS NULL AND c.eliminado = 'False'"),
    @NamedQuery(name = "Categoria.findAllMenuChild", query = "SELECT c FROM Categoria c WHERE c.categoriaPadre.id = :idCategoria"),
    @NamedQuery(name = "Categoria.findById", query = "SELECT c FROM Categoria c WHERE c.id = :id"),
    @NamedQuery(name = "Categoria.findByNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Categoria.findByNivel", query = "SELECT c FROM Categoria c WHERE c.nivel = :nivel"),
    @NamedQuery(name = "Categoria.findByEliminado", query = "SELECT c FROM Categoria c WHERE c.eliminado = :eliminado"),
    @NamedQuery(name = "Categoria.findByPosicion", query = "SELECT c FROM Categoria c WHERE c.posicion = :posicion")})
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "categoria_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NIVEL")
    private Integer nivel;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @Column(name = "POSICION")
    private Integer posicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaId")
    private Collection<Producto> productoCollection;
    @OneToMany(mappedBy = "categoriaPadre")
    private Collection<Categoria> categoriaCollection;
    @JoinColumn(name = "CATEGORIA_PADRE", referencedColumnName = "ID")
    @ManyToOne
    private Categoria categoriaPadre;

    public Categoria() {
    }

    public Categoria(Integer id) {
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @XmlTransient
    public Collection<Categoria> getCategoriaCollection() {
        return categoriaCollection;
    }

    public void setCategoriaCollection(Collection<Categoria> categoriaCollection) {
        this.categoriaCollection = categoriaCollection;
    }

    public Categoria getCategoriaPadre() {
        return categoriaPadre;
    }

    public void setCategoriaPadre(Categoria categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
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
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.Categoria[ id=" + id + " ]";
    }
    
}
