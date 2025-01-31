package tda.graph;

import tda.LinkedList.LinkedList;

public class GraphDirect extends Graph{
    private Integer nro_vertices;
    private Integer nro_edges;
    private LinkedList<Adyacencia>[] listAdyacencias;

    public GraphDirect(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        this.nro_edges = 0;
        this.listAdyacencias = new LinkedList[nro_vertices];
        for (int i = 0; i < nro_vertices; i++) {
            listAdyacencias[i] = new LinkedList<Adyacencia>();
        }
    }

  

    @Override
    public Boolean is_edge(Integer v1, Integer v2) throws Exception {
        Boolean band = false;
        if(v1.intValue() < nro_vertices && v2.intValue() < nro_vertices){
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if(!lista.isEmpty()){
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia a = matrix[i];
                    if(a.getDestination().intValue() == v2.intValue()){
                        band = true;
                        break;
                    }
                } 
            } 
        } else {
            throw new Exception("Vertices fuera de rango");
        }
        return band; 
    }

    @Override
    public Float weight_edge(Integer v1, Integer v2) throws Exception {
        Float weight = Float.NaN;
        if(is_edge(v1, v2)){
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            Adyacencia[] matrix = lista.toArray();
            for (int i = 0; i < matrix.length; i++) {
                Adyacencia a = matrix[i];
                if(a.getDestination().intValue() == v2.intValue()){
                    weight = a.getWeight();
                    break;
                }
            }
        }
        return weight;
    }

    @Override
    public void add_edge(Integer v1, Integer v2) throws Exception {
            this.add_edge(v1, v2, Float.NaN); 
    }

    @Override
    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if(v1.intValue() < nro_vertices && v2.intValue() < nro_vertices){
            if(!is_edge(v1, v2)){
                listAdyacencias[v1].add(new Adyacencia(v2, weight));
                nro_edges++;
            }
        } else {
            throw new Exception("Vertices fuera de rango");
        }
    }

    @Override
    public LinkedList<Adyacencia> adyacencias(Integer v1) {
        return listAdyacencias[v1];
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return this.listAdyacencias;
    }

    @Override
    public Integer nro_vertices() {
        return this.nro_vertices; 
    }
    public void setNro_vertices(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
    }


    @Override
    public Integer nro_edges() {
        return this.nro_edges;
    }

    public void setNro_edges(Integer nro_edges) {
        this.nro_edges = nro_edges;
    }

   
   

}
