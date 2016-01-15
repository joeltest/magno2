/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorodriguez
 */
@Entity
@Table(name = "CONSECUTIVO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consecutivo.findAll", query = "SELECT c FROM Consecutivo c"),
    @NamedQuery(name = "Consecutivo.findByNombreSucursal", query = "SELECT c FROM Consecutivo c WHERE c.nombre = :nombre AND c.sucursal.id = :idSucursal"),
    @NamedQuery(name = "Consecutivo.findById", query = "SELECT c FROM Consecutivo c WHERE c.id = :id"),
    @NamedQuery(name = "Consecutivo.findByNombre", query = "SELECT c FROM Consecutivo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Consecutivo.findByValor", query = "SELECT c FROM Consecutivo c WHERE c.valor = :valor"),
    @NamedQuery(name = "Consecutivo.findByCodigo", query = "SELECT c FROM Consecutivo c WHERE c.codigo = :codigo")})
public class Consecutivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 25)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "VALOR")
    private Integer valor;
    @Size(max = 5)
    @Column(name = "CODIGO")
    private String codigo;
    @JoinColumn(name = "SUCURSAL", referencedColumnName = "ID")
    @ManyToOne
    private Sucursal sucursal;

    public Consecutivo() {
    }

    public Consecutivo(Integer id) {
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
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
        if (!(object instanceof Consecutivo)) {
            return false;
        }
        Consecutivo other = (Consecutivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.magno.entity.Consecutivo[ id=" + id + " ]";
    }
    
}
