package tda.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tda.LinkedList.LinkedList;
import tda.exception.ListEmptyException;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E[] labels;
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(clazz, nro_vertices);
        this.dictVertices = new HashMap<E, Integer>();
    }

    public Boolean is_edgeLabel(E v1, E v2) throws Exception {
        if(isLabelGraph()) {
           return is_edge(getVerticeLabel(v1), getVerticeLabel(v2));
        } else {
            throw new Exception("Grafo no etiquetado");
        }
    }

    public void insertEdgeLabel(E v1, E v2, Float weight) throws Exception {
       if (isLabelGraph()) { 
           add_edge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
       } else {
           throw new Exception("Grafo no etiquetado");
       }
    }

    public void insertEdgeLabel(E v1, E v2) throws Exception {
        if (isLabelGraph()) {
            insertEdgeLabel(v1, v2, Float.NaN);
        } else {
            throw new Exception("Grafo no etiquetado");
        }
    }

    public LinkedList<Adyacencia> adyacenciasLabel(E v1) throws Exception {
        if (isLabelGraph()) {
            return adyacencias(getVerticeLabel(v1));
        } else {
            throw new Exception("Grafo no etiquetado");
        }
    }
     
    public void labelVertice(Integer v1, E label) {
        labels[v1] = label;
        dictVertices.put(label, v1);
        
    }

    public Boolean isLabelGraph() {
        Boolean band = true;

        for (int i = 0; i < labels.length ; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;

    }

    public Integer getVerticeLabel(E label) {
        return dictVertices.get(label);
    }

    public E getLabel(Integer v1) { 
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try{ 
            for (int i = 0; i < nro_vertices(); i++) {
                LinkedList<Adyacencia> lista = adyacencias(i);
                grafo += "V" + i + " [" + getLabel(i).toString() + "]" + "\n";
                if(!lista.isEmpty()){
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V"  + a.getDestination() + " weight " + a.getWeight() + " [" + getLabel(a.getDestination()).toString() + "]" + "\n";
                    }
                }
            }
        } catch (Exception e) {
            grafo = "Error al imprimir el grafo";
        }
        return grafo;
    }

    public String drawGraph() {
        String grafo = "var nodes = new vis.DataSet([" + "\n";
        try{ 
            for (int i = 0; i < nro_vertices(); i++) {
                grafo += "{ id: " + i + ", label: "  + "\"" + getLabel(i).toString() +  "\"" + "}," + "\n";
            }
            grafo += "]);" + "\n";
            grafo += "var edges = new vis.DataSet([" + "\n";

            for (int i = 0; i < nro_vertices(); i++) {
                LinkedList<Adyacencia> lista = adyacencias(i);
                if(!lista.isEmpty()){
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "{ from: " + i + ", to: " + a.getDestination() +  "}," + "\n";
                    }
                }
            }
            grafo += "]);" + "\n";
            grafo += "var container = document.getElementById(\"mynetwork\");" + "\n";
            grafo += "var data = {" + "\n";
            grafo += "nodes: nodes," + "\n";
            grafo += "edges: edges," + "\n";
            grafo += "};" + "\n";
            grafo += "var options = {};" + "\n";
            grafo += "var network = new vis.Network(container, data, options);" + "\n";
            FileWriter file = new FileWriter("/home/josue/Desktop/Practica4/flask/static/graph.js");
            file.write(grafo);
            file.flush();
            file.close();


        } catch (Exception e) {
            grafo = "Error al imprimir el grafo";
        }
        return grafo;
    }

        public void saveGraphAsJson() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> graphData = new HashMap<>();

    
        Map<String, Object>[] nodes = (HashMap<String, Object>[]) Array.newInstance(HashMap.class, nro_vertices());
        for (int i = 0; i < nro_vertices(); i++) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", i);
            node.put("label", getLabel(i).toString()); // Convertir label genérico a String
            nodes[i] = node;
        }
        graphData.put("nodes", nodes);

    
        Map<String, Object>[] edges = (HashMap<String, Object>[]) Array.newInstance(HashMap.class, nro_edges());
        int edgeIndex = 0;
        for (int i = 0; i < nro_vertices(); i++) {
            LinkedList<Adyacencia> lista = adyacencias(i);
            
            if (lista != null && !lista.isEmpty()) {
                Adyacencia[] ady = lista.toArray();
                for (int j = 0; j < ady.length; j++) {
                    Map<String, Object> edge = new HashMap<>();
                    Adyacencia a = ady[j];
                    edge.put("from", i);
                    edge.put("to", a.getDestination());
                    edge.put("weight", a.getWeight());
                    edges[edgeIndex++] = edge;
                }
            }
        }
        graphData.put("edges", edges);

        File file = new File("media/" + clazz.getSimpleName() + "Grafo" + ".json");
        file.getParentFile().mkdirs(); // Crea directorios si no existen
    
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(graphData, writer);
            System.out.println("Archivo JSON guardado en: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float[][] floydWarshall() throws IndexOutOfBoundsException, ListEmptyException {
        float[][] dist = new float[nro_vertices()][nro_vertices()];

        for (int i = 0; i < nro_vertices(); i++) {
            for (int j = 0; j < nro_vertices(); j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Float.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 0; i < nro_vertices(); i++) {
            LinkedList<Adyacencia> lista = adyacencias(i);
            for (int j = 0; j < lista.getSize(); j++)  {
                dist[i][lista.get(j).getDestination()] = lista.get(j).getWeight();

            }
        }

        for (int k = 0; k < nro_vertices(); k++) {
            for (int i = 0; i < nro_vertices(); i++) {
                for (int j = 0; j < nro_vertices(); j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    public float getShortestPath(int start, int end) throws Exception {
        float[][] dist = floydWarshall();
        if (dist[start][end] == Float.POSITIVE_INFINITY) {
            throw new Exception("No hay camino entre los vértices " + start + " y " + end);
        }
        return dist[start][end];
    }

   
    public float[] bellmanFord(int source) throws Exception {
        int n = nro_vertices(); 
        float[] dist = new float[n]; 
        for (int i = 0; i < n; i++) {
            dist[i] = Float.POSITIVE_INFINITY; 
        }
        dist[source] = 0; 

   
        for (int i = 1; i < n; i++) {
            for (int u = 0; u < n; u++) {
                LinkedList<Adyacencia> lista = adyacencias(u);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia a = lista.get(j);
                    int v = a.getDestination();
                    float weight = a.getWeight();
                    if (dist[u] != Float.POSITIVE_INFINITY && dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                    }
                }
            }
        }

     
        for (int u = 0; u < n; u++) {
            LinkedList<Adyacencia> lista = adyacencias(u);
            for (int j = 0; j < lista.getSize(); j++) {
                Adyacencia a = lista.get(j);
                int v = a.getDestination();
                float weight = a.getWeight();
                if (dist[u] != Float.POSITIVE_INFINITY && dist[u] + weight < dist[v]) {
                    throw new Exception("El grafo contiene un ciclo negativo");
                }
            }
        }

        return dist;
    }

  
    public float getShortestPathBellman(int source, int destination) throws Exception {
        float[] dist = bellmanFord(source);
        return dist[destination];
    }
}


