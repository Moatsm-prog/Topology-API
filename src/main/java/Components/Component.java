package Components;

abstract public class Component {

    private String id;
    private String type;

    public Component(String id , String type) {
        this.id = id;
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
