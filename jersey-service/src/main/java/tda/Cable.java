package tda;

public class Cable {
    private Integer id;
    private double resistance;     
    private double currentCapacity;  
    private double length; 


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return this.id;
    }

    public double getResistance() {
        return this.resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getCurrentCapacity() {
        return this.currentCapacity;
    }

    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
