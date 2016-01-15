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
import lombok.ToString;

/**
 *
 * @author jorodriguez
 */
@ToString
@Entity
@SequenceGenerator(sequenceName = "GEN_USUARIO_ID", name = "usuario_Seq", allocationSize = 1)
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u WHERE u.eliminado='False'"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.login", query = "SELECT u FROM Usuario u "
                    + " WHERE u.alias = :alias AND u.contrasena = :clave "
                    + " AND u.sucursalId.id = :idSucursal AND u.eliminado = 'False'"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByAlias", query = "SELECT u FROM Usuario u WHERE u.alias = :alias"),
    @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByDireccion", query = "SELECT u FROM Usuario u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByFechaGenero", query = "SELECT u FROM Usuario u WHERE u.fechaGenero = :fechaGenero"),
    @NamedQuery(name = "Usuario.findByHoraGenero", query = "SELECT u FROM Usuario u WHERE u.horaGenero = :horaGenero"),
    @NamedQuery(name = "Usuario.findByEliminado", query = "SELECT u FROM Usuario u WHERE u.eliminado = :eliminado")})
public class Usuario implements Serializable {
    @OneToMany(mappedBy = "usuario")
    private Collection<Venta> ventaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private Collection<Producto> productoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "usuario_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 20)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 25)
    @Column(name = "CONTRASENA")
    private String contrasena;
    @Size(max = 50)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 50)
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "FECHA_GENERO")
    @Temporal(TemporalType.DATE)
    private Date fechaGenero;
    @Column(name = "HORA_GENERO")
    @Temporal(TemporalType.TIME)
    private Date horaGenero;
    @Size(max = 5)
    @Column(name = "ELIMINADO")
    private String eliminado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioCancelo")
    private Collection<DetalleOrdenCompra> detalleOrdenCompraCollection;
    @OneToMany(mappedBy = "gerente")
    private Collection<Sucursal> sucursalCollection;
    @OneToMany(mappedBy = "usuarioId")
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne
    private Usuario usuarioId;
    @JoinColumn(name = "SUCURSAL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sucursal sucursalId;
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rol rolId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private Collection<DetalleOferta> detalleOfertaCollection;

    public Usuario() {
    }

    public Usuario(Integer id) {
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
    public Collection<DetalleOrdenCompra> getDetalleOrdenCompraCollection() {
        return detalleOrdenCompraCollection;
    }

    public void setDetalleOrdenCompraCollection(Collection<DetalleOrdenCompra> detalleOrdenCompraCollection) {
        this.detalleOrdenCompraCollection = detalleOrdenCompraCollection;
    }

    @XmlTransient
    public Collection<Sucursal> getSucursalCollection() {
        return sucursalCollection;
    }

    public void setSucursalCollection(Collection<Sucursal> sucursalCollection) {
        this.sucursalCollection = sucursalCollection;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Sucursal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    @XmlTransient
    public Collection<DetalleOferta> getDetalleOfertaCollection() {
        return detalleOfertaCollection;
    }

    public void setDetalleOfertaCollection(Collection<DetalleOferta> detalleOfertaCollection) {
        this.detalleOfertaCollection = detalleOfertaCollection;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.magno.pruebacrud.Usuario[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    
}
