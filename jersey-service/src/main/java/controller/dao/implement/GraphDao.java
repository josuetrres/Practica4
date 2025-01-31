package controller.dao.implement;

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
            
            if(!components.isEmpty()) {
                Component c[] = components.toArray();
                graph = new GraphLabelDirect<>(components.getSize(), Component.class);
                for (int i = 0; i < components.getSize(); i++) {
                    graph.labelVertice(i, c[i]);
                    
                }
            }
        
            graph.insertEdgeLabel(graph.getLabel(0), graph.getLabel(1), 1.0f);
            graph.insertEdgeLabel(graph.getLabel(1), graph.getLabel(2), 4.0f);
            graph.insertEdgeLabel(graph.getLabel(2), graph.getLabel(3), 2.0f);
            graph.insertEdgeLabel(graph.getLabel(3), graph.getLabel(4), 3.0f);
           
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
}
