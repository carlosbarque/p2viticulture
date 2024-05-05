package model;

import org.bson.types.ObjectId;

public class Inputdata {
    private ObjectId _id;
    private String action;

    public Inputdata() {
    }

    public Inputdata(ObjectId _id, String action) {
        this._id = _id;
        this.action = action;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Inputdata{" +
                "_id=" + _id +
                ", action='" + action + '\'' +
                '}';
    }
}
