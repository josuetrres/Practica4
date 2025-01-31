package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import controller.dao.services.ComponentServices;
import tda.Component;

@Path("component")
public class ComponentApi {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComponents() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        ComponentServices cs = new ComponentServices();
        map.put("msg", "OK");
        map.put("data",cs.listAll().toArray());
        if (cs.listAll().isEmpty()){
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }


    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveComponent(String json) {
        String jsonResponse = "";
        ComponentServices cs = new ComponentServices();
        Gson gson = new Gson();
        
        try {
    
            Component component = gson.fromJson(json, Component.class);
    
    
            cs.setComponent(component); 
            cs.save();  
    
            
            jsonResponse = "{\"msg\":\"OK\",\"data\":\"Component guardado correctamente\", \"info\":" + cs.toJson() + "}";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            jsonResponse = "{\"msg\":\"ERROR\",\"data\":\"" + e.getMessage() + "\"}";
        }
        
        return Response.ok(jsonResponse).build();
    }
    

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComponent(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        ComponentServices cs = new ComponentServices();
        
        try {
            cs.setComponent(cs.get(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        map.put("msg", "OK");
        map.put("data", cs.getComponent());

        if (cs.getComponent().getId() == null) {
            map.put("data", "No existe un component con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }


    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        ComponentServices cs = new ComponentServices();
        
        try {
        
            Component existingComponent = cs.get(Integer.parseInt(map.get("id").toString()));
            
            if (existingComponent == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe un component con ese identificador");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            
            existingComponent.setId(Integer.parseInt(map.get("id").toString()));
            existingComponent.setType(map.get("type").toString());
            existingComponent.setValue(Float.parseFloat(map.get("value").toString()));
            existingComponent.setDescription(map.get("description").toString());

            cs.setComponent(existingComponent);
            cs.update(); 
            
            res.put("msg", "OK");
            res.put("data", "Component actualizado correctamente");
            return Response.ok(res).build();
            
        } catch (Exception e) {
            System.out.println("Error al actualizar el component: " + e.toString());
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteComponent(@PathParam("id") Integer id) {
        String jsonResponse = "";
        ComponentServices cs = new ComponentServices();

        try {
            cs.deleteComponent(id);
            jsonResponse = "{\"data\":\"Component eliminado!\"}";
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"Error\",\"info\":\"" + e.getMessage() + "\"}";
        }

        return Response.ok(jsonResponse).build();
    }
}
