package it.polimi.tiw.project.beans;

public abstract class DBObject {

    private int id;
    
    public DBObject(int id) {
    	this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
