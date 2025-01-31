package tda.graph;

import tda.LinkedList.LinkedList;

public abstract class Graph {
    public abstract Integer nro_vertices();
    public abstract Integer nro_edges();
    public abstract Boolean is_edge(Integer v1, Integer v2) throws Exception;
    public abstract Float weight_edge(Integer v1, Integer v2) throws Exception;
    public abstract void add_edge(Integer v1, Integer v2) throws Exception;
    public abstract void add_edge(Integer v1, Integer v2, Float weight) throws Exception;
    public abstract LinkedList<Adyacencia> adyacencias(Integer v1);

    public String toString() {
        String grafo = "";
        try{ 
            for (int i = 0; i < nro_vertices(); i++) {
                LinkedList<Adyacencia> lista = adyacencias(i);
                grafo += "V" + i + "\n";
                if(!lista.isEmpty()){
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady" + "V"  + a.getDestination() + " weight" + a.getWeight() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            grafo = "Error al imprimir el grafo";
        }
        return grafo;
    }
    
}