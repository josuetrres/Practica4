package tda.graph;

public class GraphTest {

    public static void main(String[] args) throws Exception {
        int[] graphSizes = {10, 20, 30};
        
        for (int size : graphSizes) {
            System.out.println("Probando grafo de tamaño: " + size);

            GraphLabelDirect<String> graph = new GraphLabelDirect<>(size, String.class);
            createRandomGraph(graph, size);

     
            long startTime = System.nanoTime();
            float[] bellmanResult = graph.bellmanFord(0);
            long endTime = System.nanoTime();
            long durationBellman = endTime - startTime;
            System.out.println("Tiempo Bellman-Ford: " + durationBellman + " nanosegundos");

          
            startTime = System.nanoTime();
            float[][] floydResult = graph.floydWarshall();
            endTime = System.nanoTime();
            long durationFloyd = endTime - startTime;
            System.out.println("Tiempo Floyd-Warshall: " + durationFloyd + " nanosegundos");

            System.out.println();
        }
    }

    public static void createRandomGraph(GraphLabelDirect<String> graph, int size) throws Exception {
        for (int i = 0; i < size; i++) {
            graph.labelVertice(i, "Componente " + i);
        }

    
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (Math.random() < 0.5) {
                    graph.insertEdgeLabel("Componente " + i, "Componente " + j, (float) (Math.random() * 10));
                }
            }
        }
    }
}
