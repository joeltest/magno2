/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magno.restful.service.service;

import com.magno.magno.entity.Producto;
import com.magno.vo.ProductoJson;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jorodriguez
 */
@Stateless
@Path("com.magno.magno.entity.producto")
public class ProductoFacadeREST extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "magnoPU")
    private EntityManager em;

    public ProductoFacadeREST() {
        super(Producto.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Producto entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Producto entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Producto find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Produces({"application/json"})
    public List<ProductoJson> findAllProductoVo() {
        List<Object[]> lista = em.createNativeQuery("select p.id,\n"
                + "p.nombre,\n"
                + "m.nombre,\n"
                + "p.modelo,\n"
                + "p.descripcion,\n"
                + "p.caracteristica,\n"
                + "p.precio,\n"
                + "p.garantia\n"
                + "\n"
                + "from producto p, marca m\n"
                + "where p.marca_id = m.id\n"
                + "and p.eliminado='False'").getResultList();
        List<ProductoJson> listaporducto = new ArrayList<>();
       for(Object[] ob : lista){
           ProductoJson pro=new ProductoJson();
           pro.setId((int)ob[0]);
           pro.setNombre((String) ob[1]);
           pro.setMarca((String) ob[2]);
           pro.setModelo((String) ob[3]);
           pro.setDescripcion((String) ob[4]);
           pro.setCaracteristicas((String) ob[5]);
           pro.setPrecio((Long) ob[6]);
           pro.setGarantia((String) ob[7]);
           
           listaporducto.add(pro);
       }      
       return listaporducto;
       
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Producto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
