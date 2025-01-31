package controller.dao.services;

import controller.dao.implement.GraphDao;
import tda.Component;
import tda.graph.GraphLabelDirect;

public class GraphServices {
        private GraphDao graphDao; 

    public GraphServices() {
        this.graphDao = new GraphDao(); 
    }


    public GraphLabelDirect<Component> getGraph() {
        return graphDao.getGraph();
    }

    
    public void updateGraph() {
        graphDao.updateGraph(); 
    }


    public void addEdge(Component c1, Component c2, float weight) throws Exception {
        graphDao.addEdge(c1, c2, weight); 
    }

    public GraphDao getGraphDao() {
        return graphDao;
    }
}
