package tda.graph;

public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices);
    }

    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if(v1.intValue() <= nro_vertices() && v2.intValue() <= nro_vertices()){
            if(!is_edge(v1, v2)){
                getListAdyacencias()[1].add(new Adyacencia(v2, weight));
                setNro_edges(nro_edges() + 1);

                getListAdyacencias()[v2].add(new Adyacencia(v1, weight));
            }
        } else {
            throw new Exception("Vertices fuera de rango");
        }
    }
}
