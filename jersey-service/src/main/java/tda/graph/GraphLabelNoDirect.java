package tda.graph;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E>{
    public GraphLabelNoDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices, clazz);
    }


    public void insertEdgeLabel(E v1, E v2, Float weight) throws Exception {
        if (isLabelGraph()) { 
            add_edge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
            add_edge(getVerticeLabel(v2), getVerticeLabel(v1), weight); 
        } else {
            throw new Exception("Grafo no etiquetado");
        }
     }
}
