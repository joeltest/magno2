/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.magno.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import javax.persistence.Lob;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jorodriguez
 */
@ToString
@Entity
@SequenceGenerator(sequenceName = "GEN_PRODUCTO_ID", name = "producto_Seq", allocationSize = 1)
@Table(name = "PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findAllPrincipal", query = "SELECT p FROM Producto p WHERE p.mostrarPrincipal = 'True' AND p.eliminado='False'"),
    @NamedQuery(name = "Producto.findAllProductos", query = "SELECT p FROM Producto p "
            + " WHERE p.sucursalId.id = :idSucursal and p.eliminado = 'False' ORDER BY p.id DESC"),
    
    @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id"),
    
    @NamedQuery(name = "Producto.findAllProductosPorCategoria", query = "SELECT p FROM Producto p "
              + " WHERE p.sucursalId.id = :idSucursal AND p.categoriaId.id = :idCategoria AND p.eliminado = 'False' ORDER BY p.id DESC"),
    
    @NamedQuery(name = "Producto.findByCodigoBarras", query = "SELECT p FROM Producto p WHERE p.codigoBarras = :codigoBarras"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByGarantia", query = "SELECT p FROM Producto p WHERE p.garantia = :garantia"),
    @NamedQuery(name = "Producto.findByMarcaId", query = "SELECT p FROM Producto p WHERE p.marcaId = :marcaId"),
    @NamedQuery(name = "Producto.findByModelo", query = "SELECT p FROM Producto p WHERE p.modelo = :modelo"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByPrecioAnterior", query = "SELECT p FROM Producto p WHERE p.precioAnterior = :precioAnterior"),
    @NamedQuery(name = "Producto.findByAgotado", query = "SELECT p FROM Producto p WHERE p.agotado = :agotado"),
    @NamedQuery(name = "Producto.findByUsuarioId", query = "SELECT p FROM Producto p WHERE p.usuarioId = :usuarioId"),
    @NamedQuery(name = "Producto.findByEliminado", query = "SELECT p FROM Producto p WHERE p.eliminado = :eliminado")})
public class Producto implements Serializable {
    @Size(max = 5)
    @Column(name = "MOSTRAR_PRINCIPAL")
    private String mostrarPrincipal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private Collection<DetalleOrdenCompra> detalleOrdenCompraCollection;
    @JoinColumn(name = "SI_ARCHIVO", referencedColumnName = "ID")
    @ManyToOne
    private SiArchivo siArchivo;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "MARCA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Marca marcaId;
    @Size(max = 16384)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 16384)
    @Column(name = "CARACTERISTICA")
    private String caracteristica;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "producto_Seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
   
    @Size(max = 20)
    @Column(name = "CODIGO_BARRAS")
    private String codigoBarras;
    @Size(max = 512)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 300)
    @Column(name = "GARANTIA")
    private String garantia;
   
    @Size(max = 300)
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "PRECIO")
    private Long precio;
    @Column(name = "PRECIO_ANTERIOR")
    private Long precioAnterior;
    @Size(max = 5)
    @Column(name = "AGOTADO")
    private String agotado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ELIMINADO")
    private String eliminado;
    @JoinColumn(name = "SUCURSAL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sucursal sucursalId;
    @JoinColumn(name = "CATEGORIA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Categoria categoriaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private Collection<DetalleOferta> detalleOfertaCollection;

//    private StreamedContent  image;
    
    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Producto(Integer id, int usuarioId) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Long getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Long precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public String getAgotado() {
        return agotado;
    }

    public void setAgotado(String agotado) {
        this.agotado = agotado;
    }


    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public Sucursal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Sucursal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Categoria getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categoria categoriaId) {
        this.categoriaId = categoriaId;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Marca getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Marca marcaId) {
        this.marcaId = marcaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public SiArchivo getSiArchivo() {
        return siArchivo;
    }

    public void setSiArchivo(SiArchivo siArchivo) {
        this.siArchivo = siArchivo;
    }
    
    private StreamedContent getImageStreamedContent() {
        StreamedContent result = null;
        if (this.siArchivo != null) {
            InputStream is = new ByteArrayInputStream(this.siArchivo.getArchivo());
            result = new DefaultStreamedContent(is);
        }
        return result;
    }
    public StreamedContent getImage() {
        return getImageStreamedContent();
    }

    @XmlTransient
    public Collection<DetalleOrdenCompra> getDetalleOrdenCompraCollection() {
        return detalleOrdenCompraCollection;
    }

    public void setDetalleOrdenCompraCollection(Collection<DetalleOrdenCompra> detalleOrdenCompraCollection) {
        this.detalleOrdenCompraCollection = detalleOrdenCompraCollection;
    }

    public String getMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(String mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

   
    
}
