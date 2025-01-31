package tda;

public class Component {
    private Integer id;        
    private String type;        
    private Float value;       
    private String description;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
