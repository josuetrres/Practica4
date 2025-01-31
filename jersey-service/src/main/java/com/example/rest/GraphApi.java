package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.dao.implement.GraphDao;
import controller.dao.services.ComponentServices;
import controller.dao.services.GraphServices;
import tda.Component;

@Path("graph")
public class GraphApi {

    
@Path("/update")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response updateGraph(String json) {
    String jsonResponse = "";
    GraphServices gs = new GraphServices();
    Gson gson = new Gson();

    try {
      

        gs.updateGraph(); 

        jsonResponse = "{\"msg\":\"OK\",\"data\":\"Grafo actualizado correctamente\"}"; 

    } catch (Exception e) {
        e.printStackTrace(); 
        String errorMessage = e.getMessage().replace("\"", "\\\""); 
        jsonResponse = "{\"msg\":\"ERROR\",\"data\":\"" + errorMessage + "\"}";
    }

    return Response.ok(jsonResponse).build();
}



   @Path("/addEdge/{id1}/{id2}/{weight}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEdge(@PathParam("id1") int id1, 
                            @PathParam("id2") int id2, 
                            @PathParam("weight") float weight) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            ComponentServices cs = new ComponentServices();
    
            Component c1 = cs.get(id1); 
            Component c2 = cs.get(id2);
            
            if (c1 == null || c2 == null) {
                map.put("msg", "Error");
                map.put("data", "Uno o ambos componentes no fueron encontrados");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }

            GraphDao graphDao = new GraphDao();
            graphDao.addEdge(c1, c2, weight);
            
            map.put("msg", "OK");
            map.put("data", "Arista agregada correctamente entre los componentes con ID " + id1 + " y " + id2);
            return Response.ok(map).build();
        } catch (Exception e) {
    
            map.put("msg", "Error");
            map.put("data", "Error al agregar la arista: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraphJson() throws Exception {
        GraphServices gs = new GraphServices();

        String jsonContent = gs.getGraphDao().readFile();
        
        return Response.ok(jsonContent).build();
    }
}


