package controller.dao.implement;

import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

import javax.ws.rs.core.Response;

import controller.dao.services.ComponentServices;
import tda.Component;
import tda.LinkedList.LinkedList;
import tda.graph.GraphLabelDirect;

public class GraphDao {
    private GraphLabelDirect graph;

    public GraphDao() {
        updateGraph();
    }

    public GraphLabelDirect getGraph() {
        return this.graph;
    }

    public void setGraph(GraphLabelDirect graph) {
        this.graph = graph;
    }

    public void updateGraph() {
           try {
            ComponentServices cs = new ComponentServices();
            
            LinkedList<Component> components = cs.listAll();
            Random random = new Random();
            
            if(!components.isEmpty()) {
                Component c[] = components.toArray();
                graph = new GraphLabelDirect<>(components.getSize(), Component.class);
                for (int i = 0; i < components.getSize(); i++) {
                    graph.labelVertice(i, c[i]);    
    
                }
                for (int i = 0; i < components.getSize(); i++) {
                    for (int j = i + 1; j < components.getSize(); j++) {
                        float weight = 1 + random.nextFloat() * 100; 
                        graph.insertEdgeLabel(graph.getLabel(i), graph.getLabel(j), weight);
        
                    }
                }
            }
        
        


            graph.drawGraph();
            graph.saveGraphAsJson();

            System.out.println(graph.toString());
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }

    }

    public void addEdge(Component c1, Component c2, float weight) throws Exception {
        if (graph != null) {
            graph.insertEdgeLabel(c1, c2, weight);
        } else {
            throw new Exception("El grafo no está inicializado.");
        }
    }

    public String readFile() throws Exception {
    File file = new File("media/ComponentGrafo.json");

    if (!file.exists()) {
        System.out.println("El archivo no existe.");
        return "{}"; 
    }

    StringBuilder sb = new StringBuilder();
    try (Scanner in = new Scanner(new FileReader(file))) {
        while (in.hasNextLine()) {
            sb.append(in.nextLine()).append("\n");
        }
    }
    return sb.toString().trim();
}
}
