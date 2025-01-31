package com.example.rest;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.dao.services.ComponentServices;
import tda.Component;
import tda.LinkedList.LinkedList;
import tda.graph.GraphLabelDirect;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @Path("test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, String> mapa = new HashMap<>();
        GraphLabelDirect<Component> graph = null;
        

        try {
            ComponentServices cs = new ComponentServices();
            
            LinkedList<Component> components = cs.listAll();
            
            if(!components.isEmpty()) {
                Component c[] = components.toArray();
                graph = new GraphLabelDirect<>(components.getSize(), Component.class);
                for (int i = 0; i < components.getSize(); i++) {
                    graph.labelVertice(i, c[i]);
                    
                }
            }
            graph.insertEdgeLabel(components.get(0), components.get(1), 1.0f);
            graph.drawGraph();
            graph.saveGraphAsJson();

            System.out.println(graph.toString());
        
        } catch (Exception e) {
            e.printStackTrace(); 
            mapa.put("error", "Ocurrió un error al procesar el grafo: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }


        mapa.put("msg", "Ok");
        return Response.ok(mapa).build();
    }




    
}