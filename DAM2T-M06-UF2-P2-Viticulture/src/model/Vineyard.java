package model;

import org.bson.types.ObjectId;

public class Vineyard {
    private ObjectId _id;
    private String name;
    private boolean collected;

    // Constructor vacío requerido por el mapeador de MongoDB
    public Vineyard() {
    }

    public Vineyard(String name, boolean collected) {
        this.name = name;
        this.collected = collected;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    @Override
    public String toString() {
        return "Vineyard{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", collected=" + collected +
                '}';
    }
}
