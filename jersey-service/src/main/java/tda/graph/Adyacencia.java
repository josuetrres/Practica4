package tda.graph;

public class Adyacencia {

    private Integer destination;
    private Float weight;

    public Adyacencia(Integer destination, Float weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Adyacencia() {
    }

    public Integer getDestination() {
        return this.destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

}
